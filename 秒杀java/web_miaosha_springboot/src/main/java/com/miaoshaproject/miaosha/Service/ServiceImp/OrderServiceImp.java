package com.miaoshaproject.miaosha.Service.ServiceImp;

import com.miaoshaproject.miaosha.Dao.OrderInfoDOMapper;
import com.miaoshaproject.miaosha.Dao.SequenceInfoDOMapper;
import com.miaoshaproject.miaosha.Dao.StockLogDOMapper;
import com.miaoshaproject.miaosha.DataObject.OrderInfoDO;
import com.miaoshaproject.miaosha.DataObject.SequenceInfoDO;
import com.miaoshaproject.miaosha.DataObject.StockLogDO;
import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Error.EnumBussinessError;
import com.miaoshaproject.miaosha.Service.ItemService;
import com.miaoshaproject.miaosha.Service.Model.ItemModel;
import com.miaoshaproject.miaosha.Service.Model.OrderModel;
import com.miaoshaproject.miaosha.Service.Model.UserModel;
import com.miaoshaproject.miaosha.Service.OrderService;
import com.miaoshaproject.miaosha.Service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImp  implements OrderService {

    @Autowired
    private SequenceInfoDOMapper sequenceDOMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderInfoDOMapper orderDOMapper;

    @Autowired
    private StockLogDOMapper stockLogDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount,String stockLogId) throws BusinessException {
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }
        if(amount <= 0 || amount > 99){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");
        }

        if(promoId != null){
            if(promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }else if(itemModel.getPromoModel().getStatus().intValue() != 2) {
                throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"活动信息还未开始");
            }
        }
//        boolean result = itemService.decreaseStock(itemId,amount);
//        if(!result){
//            throw new BusinessException(EnumBussinessError.STOCK_NOT_ENOUGH);
//        }

        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if(promoId != null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));


        orderModel.setId(generateOrderNo());
        OrderInfoDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        itemService.increaseSales(itemId,amount);

        StockLogDO stockLogDO = stockLogDOMapper.selectByPrimaryKey(stockLogId);
        if(stockLogDO == null){
            throw new BusinessException(EnumBussinessError.UNKNOWN_ERROR);
        }
        stockLogDO.setStatus(2);
        stockLogDOMapper.updateByPrimaryKeySelective(stockLogDO);

        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo(){
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        int sequence = 0;
        SequenceInfoDO sequenceDO =  sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for(int i = 0; i < 6-sequenceStr.length();i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private OrderInfoDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderInfoDO orderDO = new OrderInfoDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}

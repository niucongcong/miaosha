package org.example.Service.ServiceImp.ServiceImp;

import org.example.Dao.OrderInfoDOMapper;
import org.example.Dao.UserInfoDOMapper;
import org.example.DataObject.OrderInfoDO;
import org.example.DataObject.UserInfoDO;
import org.example.Error.BusinessException;
import org.example.Error.EnumBussinessError;
import org.example.Response.Model.ItemModel;
import org.example.Response.Model.OrderModel;
import org.example.Service.ServiceImp.ItemService;
import org.example.Service.ServiceImp.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp  implements OrderService {


    @Autowired
    private OrderInfoDOMapper orderInfoDOMapper;

    @Autowired
    UserInfoDOMapper userInfoDOMapper;

    @Autowired
    private ItemService itemService;


    @Override
    public ArrayList<OrderModel> getAllItem(){
        ArrayList<OrderInfoDO> data = orderInfoDOMapper.selectAllOrderInfo();
        ArrayList<OrderModel> itemModelList = (ArrayList<OrderModel>) data.stream().map(orderInfoDO -> {
            int itemId=orderInfoDO.getItemId();
            OrderModel orderModel=new OrderModel();
            orderModel.setId(orderInfoDO.getId());
            orderModel.setAmount(orderInfoDO.getAmount());
            orderModel.setTotalPrice(orderInfoDO.getOrderPrice());
            orderModel.setUserId(orderInfoDO.getUserId());
            UserInfoDO userInfoDo= userInfoDOMapper.selectByPrimaryKey(orderInfoDO.getUserId());
            if (userInfoDo==null){
                try {
                    throw new BusinessException(EnumBussinessError.USER_NOT_EXIST);
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
            }
            orderModel.setUserName(userInfoDo.getName());
            ItemModel itemModel=new ItemModel();
            try {
               itemModel=itemService.getItemById(itemId);
            } catch (BusinessException e) {
                e.printStackTrace();
            }
            orderModel.setItemModel(itemModel);
            return orderModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }
}

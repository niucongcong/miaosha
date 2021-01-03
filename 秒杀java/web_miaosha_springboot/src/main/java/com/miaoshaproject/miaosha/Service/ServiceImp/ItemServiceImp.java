package com.miaoshaproject.miaosha.Service.ServiceImp;

import com.miaoshaproject.miaosha.Dao.ItemDOMapper;
import com.miaoshaproject.miaosha.Dao.ItemStockDOMapper;
import com.miaoshaproject.miaosha.Dao.StockLogDOMapper;
import com.miaoshaproject.miaosha.DataObject.ItemDO;
import com.miaoshaproject.miaosha.DataObject.ItemStockDO;
import com.miaoshaproject.miaosha.DataObject.StockLogDO;
import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Error.EnumBussinessError;
import com.miaoshaproject.miaosha.Mq.Producer;
import com.miaoshaproject.miaosha.Service.ItemService;
import com.miaoshaproject.miaosha.Service.Model.ItemModel;
import com.miaoshaproject.miaosha.Service.Model.PromoModel;
import com.miaoshaproject.miaosha.Service.PromoService;
import com.miaoshaproject.miaosha.Validator.ValidationResult;
import com.miaoshaproject.miaosha.Validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    Producer producer;

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private PromoService promoService;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StockLogDOMapper stockLogDOMapper;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        ValidationResult result=validator.validate(itemModel);
        if (result.isHasErrors()){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        ItemDO itemDO=this.convertItemDOFromItemModel(itemModel);
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = this.convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        List<ItemModel> itemModelList =  itemDOList.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);
            PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
            if(promoModel != null && promoModel.getStatus().intValue() != 3){
                itemModel.setPromoModel(promoModel);
            }
            return itemModel;
        }).collect(Collectors.toList());
        return itemModelList;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null){
            return null;
        }
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        ItemModel itemModel = convertModelFromDataObject(itemDO,itemStockDO);
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if(promoModel != null && promoModel.getStatus().intValue() != 3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    public ItemModel getItemByIdInCache(Integer id) {
        ItemModel itemModel = (ItemModel) redisTemplate.opsForValue().get("item_validate_"+id);
        if(itemModel == null){
            itemModel = this.getItemById(id);
            redisTemplate.opsForValue().set("item_validate_"+id,itemModel);
            redisTemplate.expire("item_validate_"+id,10, TimeUnit.MINUTES);
        }
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) throws BusinessException {
        long result = redisTemplate.opsForValue().increment("promo_item_stock_"+itemId,amount.intValue() * -1);
        if(result >0){
            return true;
        }else if(result == 0){
            redisTemplate.opsForValue().set("promo_item_stock_invalid_"+itemId,"true");
            return true;
        }else{
            increaseStock(itemId,amount);
            return false;
        }
    }

    @Override
    public boolean increaseStock(Integer itemId, Integer amount) throws BusinessException {
        redisTemplate.opsForValue().increment("promo_item_stock_"+itemId,amount.intValue());
        return true;
    }

    @Override
    public boolean asyncDecreaseStock(Integer itemId, Integer amount) {
        boolean mqResult = producer.asyncReduceStock(itemId,amount);
        return mqResult;
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException {
        itemDOMapper.increaseSales(itemId,amount);
    }

    @Override
    public String initStockLog(Integer itemId, Integer amount) {
        StockLogDO stockLogDO = new StockLogDO();
        stockLogDO.setItemId(itemId);
        stockLogDO.setAmount(amount);
        stockLogDO.setStockLogId(UUID.randomUUID().toString().replace("-",""));
        stockLogDO.setStatus(1);
        stockLogDOMapper.insertSelective(stockLogDO);
        return stockLogDO.getStockLogId();
    }

    private ItemDO convertItemDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }
    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());
        return itemStockDO;
    }
    private ItemModel convertModelFromDataObject(ItemDO itemDO,ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}

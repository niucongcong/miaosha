package org.example.Service.ServiceImp.ServiceImp;

import org.example.Dao.ItemDOMapper;
import org.example.Dao.ItemStockDOMapper;
import org.example.Dao.PromoDOMapper;
import org.example.DataObject.ItemDO;
import org.example.DataObject.ItemStockDO;
import org.example.DataObject.PromoDO;
import org.example.Error.BusinessException;
import org.example.Error.EnumBussinessError;
import org.example.Response.CommonReturnType;
import org.example.Response.Model.ItemModel;
import org.example.Response.Model.PromoModel;
import org.example.Service.ServiceImp.ItemService;
import org.example.Service.ServiceImp.PromoService;
import org.example.Vaildator.ValidatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class ItemServiceImp implements ItemService {

    private static Logger logger= LoggerFactory.getLogger(ItemServiceImp.class);

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private PromoService promoService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private PromoDOMapper promoDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ItemModel getItemById(Integer id) throws BusinessException {
        ItemDO itemDO=itemDOMapper.selectByPrimaryKey(id);
        if (itemDO==null){
            throw new BusinessException(EnumBussinessError.ITEM_NOT_EXITS);
        }
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        ItemModel itemModel = this.convertModelFromDataObject(itemDO,itemStockDO);
        return  itemModel;
    }

    @Override
    public ArrayList<ItemModel> getAllItem() {
        ArrayList<ItemDO> itemDOList = itemDOMapper.selectAllItem();
        ArrayList<ItemModel> itemModelList = (ArrayList<ItemModel>) itemDOList.stream().map(itemDO -> {
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
    public CommonReturnType publishItem(Integer itemId,String promoName,Double price) throws ParseException, BusinessException {
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel==null){
            return CommonReturnType.create("商品信息不存在");
        }

        PromoDO promoDO=new PromoDO();
        promoDO.setItemId(itemId);
        promoDO.setPromoItemPrice(price);
        promoDO.setPromoName(promoName);
        String startDate="2020-11-18 00:32:39";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//注意月份是MM
        Date date_start = simpleDateFormat.parse(startDate);
        promoDO.setStartDate(date_start);
        String endDate="2025-11-18 00:32:39";
        Date date_end = simpleDateFormat.parse(endDate);
        promoDO.setEndDate(date_end);

        int state= promoDOMapper.insert(promoDO);

        if (state==0){
            return CommonReturnType.create("发布失败");
        }

        redisTemplate.opsForValue().set("promo_item_stock_"+itemModel.getId(), itemModel.getStock());
        redisTemplate.delete("promo_item_stock_invalid_"+itemModel.getId());
        return CommonReturnType.create("发布成功");
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

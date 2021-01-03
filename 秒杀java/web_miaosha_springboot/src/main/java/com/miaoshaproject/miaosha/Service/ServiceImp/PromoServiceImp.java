package com.miaoshaproject.miaosha.Service.ServiceImp;

import com.miaoshaproject.miaosha.Dao.PromoDOMapper;
import com.miaoshaproject.miaosha.DataObject.PromoDO;
import com.miaoshaproject.miaosha.Service.ItemService;
import com.miaoshaproject.miaosha.Service.Model.ItemModel;
import com.miaoshaproject.miaosha.Service.Model.PromoModel;
import com.miaoshaproject.miaosha.Service.Model.UserModel;
import com.miaoshaproject.miaosha.Service.PromoService;
import com.miaoshaproject.miaosha.Service.UserService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class PromoServiceImp implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Autowired
    private ItemService itemService;
    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promoDO=promoDOMapper.selectByItemId(itemId);
        PromoModel promoModel=convertFromPromoDO(promoDO);
        if (promoModel==null) return  null;
        if (promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if (promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    @Override
    public void publishPromo(Integer promoId) {
        PromoDO promoDO = promoDOMapper.selectByPrimaryKey(promoId);
        if(promoDO.getItemId() == null || promoDO.getItemId().intValue() == 0){
            return;
        }
        ItemModel itemModel = itemService.getItemById(promoDO.getItemId());
        redisTemplate.opsForValue().set("promo_item_stock_"+itemModel.getId(), itemModel.getStock());
        redisTemplate.delete("promo_item_stock_invalid_"+itemModel.getId());
    }

    @Override
    public String generateSecondKillToken(Integer promoId, Integer itemId, Integer userId) {
        if(redisTemplate.hasKey("promo_item_stock_invalid_"+itemId)){
            return null;
        }
        PromoDO promoDO = promoDOMapper.selectByPrimaryKey(promoId);
        PromoModel promoModel = convertFromPromoDO(promoDO);
        if(promoModel == null){
            return null;
        }
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        if(promoModel.getStatus().intValue() != 2){
            return null;
        }
        ItemModel itemModel = itemService.getItemByIdInCache(itemId);
        if(itemModel == null){
            return null;
        }

        UserModel userModel = userService.getUserByIdInCache(userId);
        if(userModel == null){
            return null;
        }
        String token = UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set("promo_token_"+promoId+"_userid_"+userId+"_itemid_"+itemId,token);
        redisTemplate.expire("promo_token_"+promoId+"_userid_"+userId+"_itemid_"+itemId,5, TimeUnit.MINUTES);
        return token;
    }

    private PromoModel convertFromPromoDO(PromoDO promoDO){
        if(promoDO == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO, promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}

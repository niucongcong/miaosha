package com.miaoshaproject.miaosha.Service;

import com.miaoshaproject.miaosha.Service.Model.PromoModel;

public interface PromoService {
    PromoModel getPromoByItemId(Integer itemId);
    void publishPromo(Integer promoId);
    String generateSecondKillToken(Integer promoId,Integer itemId,Integer userId);
}

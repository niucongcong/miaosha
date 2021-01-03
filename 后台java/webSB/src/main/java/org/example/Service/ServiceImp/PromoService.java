package org.example.Service.ServiceImp;


import org.example.Response.Model.PromoModel;

public interface PromoService {
    PromoModel getPromoByItemId(Integer itemId);
    void publishPromo(Integer promoId);
    String generateSecondKillToken(Integer promoId,Integer itemId,Integer userId);
}

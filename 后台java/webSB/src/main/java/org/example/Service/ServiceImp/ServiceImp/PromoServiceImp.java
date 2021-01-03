package org.example.Service.ServiceImp.ServiceImp;


import org.example.Dao.PromoDOMapper;
import org.example.DataObject.PromoDO;
import org.example.Response.Model.PromoModel;
import org.example.Service.ServiceImp.PromoService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class PromoServiceImp implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promoDO=promoDOMapper.selectByItemId(itemId);
        PromoModel promoModel=convertFromPromoDO(promoDO);
        if (promoModel==null) return  null;
        promoModel.setStatus(2);
        return promoModel;
    }

    @Override
    public void publishPromo(Integer promoId) {

    }

    @Override
    public String generateSecondKillToken(Integer promoId, Integer itemId, Integer userId) {
        return null;
    }

    private PromoModel convertFromPromoDO(PromoDO promoDO){
        if(promoDO == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        promoModel.setId(promoDO.getId());
        promoModel.setItemId(promoDO.getItemId());
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        promoModel.setPromoName(promoDO.getPromoName());
        return promoModel;
    }
}

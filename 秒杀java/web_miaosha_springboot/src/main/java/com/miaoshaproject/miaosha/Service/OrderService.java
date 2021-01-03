package com.miaoshaproject.miaosha.Service;

import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Service.Model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount,String stockLogId) throws BusinessException;
    public String generateOrderNo();
}

package com.miaoshaproject.miaosha.Service.Model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderModel {
    private String id;
    private Integer userId;
    private Integer itemId;
    private Integer promoId;
    private BigDecimal itemPrice;
    private Integer amount;
    private BigDecimal orderPrice;
}

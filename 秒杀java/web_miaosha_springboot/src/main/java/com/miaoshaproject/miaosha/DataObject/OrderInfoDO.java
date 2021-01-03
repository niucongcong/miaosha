package com.miaoshaproject.miaosha.DataObject;

import lombok.Data;

@Data
public class OrderInfoDO {
    private String id;
    private Integer userId;
    private Integer itemId;
    private Double itemPrice;
    private Integer amount;
    private Double orderPrice;
    private Integer promoId;
}
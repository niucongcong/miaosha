package com.miaoshaproject.miaosha.DataObject;

import lombok.Data;

@Data
public class ItemDO {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private Integer sales;
    private String imgUrl;
}
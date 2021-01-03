package com.miaoshaproject.miaosha.Controllers.ViewModel;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class ItemViewModel {
    private Integer id;
    private String title;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private Integer sales;
    private String imgUrl;
    private Integer promoStatus;
    private BigDecimal promoPrice;
    private Integer promoId;
    private String startDate;
}

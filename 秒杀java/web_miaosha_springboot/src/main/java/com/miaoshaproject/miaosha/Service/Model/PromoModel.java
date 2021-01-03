package com.miaoshaproject.miaosha.Service.Model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;


@Data
public class PromoModel {
    private Integer id;

    private Integer status;

    private String promoName;

    private DateTime startDate;

    private DateTime endDate;

    private Integer itemId;

    private BigDecimal promoItemPrice;

}

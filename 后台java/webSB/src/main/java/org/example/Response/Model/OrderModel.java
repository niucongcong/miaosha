package org.example.Response.Model;

import lombok.Data;


@Data
public class OrderModel {
    private String id;
    private Integer userId;
    private String userName;
    private ItemModel itemModel;
    private Integer promoId;
    private Integer amount;
    private Double totalPrice;
}

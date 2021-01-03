package com.miaoshaproject.miaosha.DataObject;


import lombok.Data;

@Data
public class UserDO {
    private Integer id;
    private String name;
    private Integer gender;
    private Integer age;
    private String telphone;
    private String registerMode;
    private String thirdPartyId;
}
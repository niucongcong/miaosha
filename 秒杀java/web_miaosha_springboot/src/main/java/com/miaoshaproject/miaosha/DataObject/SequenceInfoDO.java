package com.miaoshaproject.miaosha.DataObject;

import lombok.Data;

@Data
public class SequenceInfoDO {
    private String name;
    private Integer currentValue;
    private Integer step;
}
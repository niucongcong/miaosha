package com.miaoshaproject.miaosha.Dao;

import com.miaoshaproject.miaosha.DataObject.OrderInfoDO;

public interface OrderInfoDOMapper {
    int deleteByPrimaryKey(String id);
    int insert(OrderInfoDO record);
    int insertSelective(OrderInfoDO record);
    OrderInfoDO selectByPrimaryKey(String id);
    int updateByPrimaryKeySelective(OrderInfoDO record);
    int updateByPrimaryKey(OrderInfoDO record);
}
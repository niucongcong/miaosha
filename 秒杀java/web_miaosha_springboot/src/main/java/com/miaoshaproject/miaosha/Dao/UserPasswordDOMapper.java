package com.miaoshaproject.miaosha.Dao;

import com.miaoshaproject.miaosha.DataObject.UserPasswordDO;

public interface UserPasswordDOMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(UserPasswordDO record);
    int insertSelective(UserPasswordDO record);
    UserPasswordDO selectByPrimaryKey(Integer id);
    UserPasswordDO selectByUserId(Integer id);
    int updateByPrimaryKeySelective(UserPasswordDO record);
    int updateByPrimaryKey(UserPasswordDO record);
}
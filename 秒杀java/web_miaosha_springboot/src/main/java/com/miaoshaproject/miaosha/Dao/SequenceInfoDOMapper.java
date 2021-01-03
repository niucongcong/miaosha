package com.miaoshaproject.miaosha.Dao;

import com.miaoshaproject.miaosha.DataObject.SequenceInfoDO;

public interface SequenceInfoDOMapper {
    int deleteByPrimaryKey(String name);
    int insert(SequenceInfoDO record);
    int insertSelective(SequenceInfoDO record);
    SequenceInfoDO selectByPrimaryKey(String name);
    SequenceInfoDO getSequenceByName(String name);
    int updateByPrimaryKeySelective(SequenceInfoDO record);
    int updateByPrimaryKey(SequenceInfoDO record);
}
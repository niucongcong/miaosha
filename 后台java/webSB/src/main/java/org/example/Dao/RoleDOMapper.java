package org.example.Dao;

import org.example.DataObject.RoleDO;

public interface RoleDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated Wed Dec 23 13:17:26 CST 2020
     */
    int deleteByPrimaryKey(Integer roleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated Wed Dec 23 13:17:26 CST 2020
     */
    int insert(RoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated Wed Dec 23 13:17:26 CST 2020
     */
    int insertSelective(RoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated Wed Dec 23 13:17:26 CST 2020
     */
    RoleDO selectByPrimaryKey(Integer roleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated Wed Dec 23 13:17:26 CST 2020
     */
    int updateByPrimaryKeySelective(RoleDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table role
     *
     * @mbg.generated Wed Dec 23 13:17:26 CST 2020
     */
    int updateByPrimaryKey(RoleDO record);
}
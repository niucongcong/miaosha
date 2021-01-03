package com.miaoshaproject.miaosha.Service;

import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Service.Model.UserModel;

public interface UserService {
    UserModel getUserById(int id);
    UserModel getUserByIdInCache(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telphone,String encrptPassword) throws BusinessException;
}

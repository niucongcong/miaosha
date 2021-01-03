package org.example.Service.ServiceImp;


import org.example.DataObject.AllUserDO;
import org.example.Error.BusinessException;
import org.example.Response.Model.UserModel;

public interface UserService {
    UserModel getUserById(int id);
    UserModel getUserByIdInCache(Integer id);
    void register(UserModel userModel) throws BusinessException;
    AllUserDO validateLogin(String username, String password) throws BusinessException;
}

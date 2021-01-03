package org.example.Service.ServiceImp.ServiceImp;


import org.example.Dao.AllUserDOMapper;
import org.example.DataObject.AllUserDO;
import org.example.Error.BusinessException;
import org.example.Error.EnumBussinessError;
import org.example.Response.Model.UserModel;
import org.example.Service.ServiceImp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp  implements UserService {

    @Autowired
    private AllUserDOMapper userDOMapper;

    @Override
    public UserModel getUserById(int id) {
        return null;
    }

    @Override
    public UserModel getUserByIdInCache(Integer id) {
        return null;
    }

    @Override
    public void register(UserModel userModel) throws BusinessException {

    }

    @Override
    public AllUserDO validateLogin(String username, String password) throws BusinessException {
        AllUserDO userDO = userDOMapper.selectByUserName(username,password);
        if(userDO == null){
            throw new BusinessException(EnumBussinessError.USER_LOGIN_FAIL);
        }
        return userDO;
    }
}

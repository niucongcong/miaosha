package com.miaoshaproject.miaosha.Service.ServiceImp;

import com.miaoshaproject.miaosha.Dao.UserDOMapper;
import com.miaoshaproject.miaosha.Dao.UserPasswordDOMapper;
import com.miaoshaproject.miaosha.DataObject.UserDO;
import com.miaoshaproject.miaosha.DataObject.UserPasswordDO;
import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Error.EnumBussinessError;
import com.miaoshaproject.miaosha.Service.Model.UserModel;
import com.miaoshaproject.miaosha.Service.UserService;
import com.miaoshaproject.miaosha.Validator.ValidationResult;
import com.miaoshaproject.miaosha.Validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImp  implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserModel getUserById(int id) {
        UserDO userDO=userDOMapper.selectByPrimaryKey(id);
        UserPasswordDO userPasswordDO=userPasswordDOMapper.selectByUserId(id);
        if(userDO==null || userPasswordDO==null) return null;
        return convertFromDataObjectToUserModel(userDO,userPasswordDO);
    }

    @Override
    public UserModel getUserByIdInCache(Integer id) {
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get("user_validate_"+id);
        if(userModel == null){
            userModel = this.getUserById(id);
            redisTemplate.opsForValue().set("user_validate_"+id,userModel);
            redisTemplate.expire("user_validate_"+id,10, TimeUnit.MINUTES);
        }
        return userModel;
    }


    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException {
        if (userModel==null) throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR);

        ValidationResult validationResult=validator.validate(userModel);
        if (validationResult.isHasErrors()){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,validationResult.getErrMsg());
        }

        UserDO userDO=convertFromUserModelToUserDO(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException exception){
            throw new BusinessException(EnumBussinessError.USER_HAS_EXIST);
        }
        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO=convertFromUserModelToUserPasswordDO(userModel);
        userPasswordDOMapper.insertSelective(userPasswordDO);
        return;
    }

    @Override
    public UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException {
        UserDO userDO = userDOMapper.selectByTelephone(telphone);
        if(userDO == null){
            throw new BusinessException(EnumBussinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObjectToUserModel(userDO,userPasswordDO);
        if(!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())){
            throw new BusinessException(EnumBussinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }



    private UserDO convertFromUserModelToUserDO(UserModel userModel){
        if (userModel==null) return null;
        UserDO userDO=new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    private UserPasswordDO convertFromUserModelToUserPasswordDO(UserModel userModel){
        if (userModel==null) return null;
        UserPasswordDO userPasswordDO=new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());
        return userPasswordDO;
    }

    private UserModel convertFromDataObjectToUserModel(UserDO userDO , UserPasswordDO userPasswordDO){
        if(userDO==null || userPasswordDO==null) return null;
        UserModel userModel=new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());
        return userModel;
    }
}

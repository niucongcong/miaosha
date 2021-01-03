package com.miaoshaproject.miaosha.Controllers;


import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.miaosha.Controllers.ViewModel.UserViewModel;
import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Error.EnumBussinessError;
import com.miaoshaproject.miaosha.Response.CommonReturnType;
import com.miaoshaproject.miaosha.Service.Model.UserModel;
import com.miaoshaproject.miaosha.Service.UserService;
import com.miaoshaproject.miaosha.Utils.SelfUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller("user")
@RequestMapping("/user")
//单纯使用@CrossOrigin并没有解决跨域，需要添加后面的allowCredentials = "true", allowedHeaders = "*"
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends  BaseController{

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    private String apiUrl = "https://sms_developer.zhenzikj.com";
    private String appId = "107142";
    private String appSecret = "445b800f-81b6-4bb8-8883-1ba96aabe6e7";

    @Autowired
    private UserService userService;


    @Resource
    RedisTemplate redisTemplate;

    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="otpCode")String optCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="age")Integer age,
                                     @RequestParam(name="telephone")String telephone,
                                     @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String inSessionOptCode=(String) redisTemplate.opsForValue().get(telephone);
        if(inSessionOptCode==null|| !StringUtils.equals(inSessionOptCode, optCode)){
            throw  new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel=new UserModel();
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelphone(telephone);
        userModel.setRegisterMode("byPhone");
        userModel.setEncrptPassword(SelfUtils.EncodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }


    @RequestMapping(value = "/login")
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telephone")String telephone,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(telephone)||StringUtils.isEmpty(password)){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR);
        }
        logger.info(telephone+"->>>>"+password);
        UserModel userModel=userService.validateLogin(telephone, SelfUtils.EncodeByMd5(password));

        String uuid= UUID.randomUUID().toString();
        uuid=uuid.replace("-","");
        redisTemplate.opsForValue().set(uuid,userModel);
        redisTemplate.expire(uuid,1,TimeUnit.HOURS);
        return CommonReturnType.create(uuid);
    }

    @RequestMapping(value = "/getOpt",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType getOpt(@RequestParam(name="telephone")String telephone) throws Exception {
        ZhenziSmsClient client = new ZhenziSmsClient(apiUrl, appId, appSecret);
        String code = String.valueOf(new Random().nextInt(999999));
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", telephone);
        params.put("templateId", "2257");
        String[] templateParams = new String[2];
        templateParams[0] = code;
        templateParams[1] = "5分钟";
        params.put("templateParams", templateParams);
        String result = client.send(params);
        System.out.println(result);
        redisTemplate.opsForValue().set(telephone,code,5, TimeUnit.MINUTES);
        LinkedHashMap map= (LinkedHashMap) new JSONParser(result).parse();
        map.put("netVerifyCode",code);
        return CommonReturnType.create(map, "success");
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(value = "id")int user_id) throws BusinessException {
        UserModel userModel = userService.getUserById(user_id);
        if(userModel==null){
            throw  new BusinessException(EnumBussinessError.USER_NOT_EXIST);
        }
        UserViewModel userViewModel=convertFromUserModel(userModel);
        return CommonReturnType.create(userViewModel);
    }
    private UserViewModel convertFromUserModel(UserModel userModel){
        if(userModel==null) return null;
        UserViewModel userViewModel=new UserViewModel();
        BeanUtils.copyProperties(userModel, userViewModel);
        return userViewModel;
    }


}

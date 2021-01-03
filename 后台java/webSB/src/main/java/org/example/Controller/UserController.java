package org.example.Controller;


import com.alibaba.druid.util.StringUtils;
import org.example.DataObject.AllUserDO;
import org.example.DataObject.RoleDO;
import org.example.Error.BusinessException;
import org.example.Response.CommonReturnType;
import org.example.Response.Model.UserModel;
import org.example.Service.ServiceImp.RoleService;
import org.example.Service.ServiceImp.UserService;
import org.example.Utils.CodeUtil;
import org.example.Utils.SelfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller("user")
@RequestMapping("/user")
//单纯使用@CrossOrigin并没有解决跨域，需要添加后面的allowCredentials = "true", allowedHeaders = "*"
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController {

    private static Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Autowired
    RedisTemplate redisTemplate;

    private  String code="";

    @RequestMapping(value = "/login")
    @ResponseBody
    public CommonReturnType login(HttpServletRequest request,@RequestParam(name="username")String username,
                                  @RequestParam(name="password")String password,
                                  @RequestParam(name="verifyCode")String verifyCode) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(username)||StringUtils.isEmpty(password)||StringUtils.isEmpty(verifyCode)){
            return CommonReturnType.create("请输入正确的用户名或密码","failed");
        }
        String ip=getIpAddr(request);
        String code= (String) redisTemplate.opsForValue().get("verify_code_"+ip);
        logger.info(verifyCode);
        logger.info(code);
        if (!verifyCode.equals(code)){
            return CommonReturnType.create("请输入正确验证码","failed");
        }

        String uuid= UUID.randomUUID().toString();
        uuid=uuid.replace("-","");
        logger.info(username+"->>>>"+password);
        AllUserDO allUserDO= userService.validateLogin(username, SelfUtils.EncodeByMd5(password));

        Integer integer=allUserDO.getId().intValue();

        redisTemplate.opsForValue().set("user_id"+integer,uuid);
        redisTemplate.expire(uuid,1, TimeUnit.HOURS);


        int roleId=allUserDO.getRoleId();
        RoleDO roleDO=roleService.selectByPrimaryKey(roleId);
        UserModel userModel=new UserModel();
        userModel.setId(allUserDO.getId().intValue());
        userModel.setRole_name(roleDO.getRoleName());

        redisTemplate.opsForValue().set("role_name_"+integer,roleDO.getRoleName());
        redisTemplate.expire("role_name_"+integer,3,TimeUnit.MINUTES);

        userModel.setUserName(allUserDO.getName());
        userModel.setToken(uuid);
        return CommonReturnType.create(userModel);
    }

    @Autowired
    private HttpServletRequest httpServletRequest;

    //生成验证码
    @RequestMapping(value = "/generateverifycode",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void generateverifycode(HttpServletRequest request,HttpServletResponse response) throws BusinessException, IOException {
        Map<String,Object> map = CodeUtil.generateCodeAndPic();
        String ip=getIpAddr(request);
        redisTemplate.opsForValue().set("verify_code_"+ip,map.get("code"));
        redisTemplate.expire("verify_code_"+ip,3,TimeUnit.MINUTES);
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", response.getOutputStream());
    }


    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    }

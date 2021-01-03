package com.miaoshaproject.miaosha.Controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Error.EnumBussinessError;
import com.miaoshaproject.miaosha.Mq.Producer;
import com.miaoshaproject.miaosha.Response.CommonReturnType;
import com.miaoshaproject.miaosha.Service.ItemService;
import com.miaoshaproject.miaosha.Service.Model.OrderModel;
import com.miaoshaproject.miaosha.Service.Model.UserModel;
import com.miaoshaproject.miaosha.Service.OrderService;
import com.miaoshaproject.miaosha.Service.PromoService;
import com.miaoshaproject.miaosha.Utils.CodeUtil;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends  BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    private Producer producer;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PromoService promoService;

    private ExecutorService executorService;

    @PostConstruct
    public void init(){
        executorService = Executors.newFixedThreadPool(200);
    }



    @RequestMapping(value = "/generatetoken",method = {RequestMethod.POST},consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType generatetoken(@RequestParam(name="itemId")Integer itemId,
                                          @RequestParam(name="promoId")Integer promoId) throws BusinessException {
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if(org.springframework.util.StringUtils.isEmpty(token)){
            throw new BusinessException(EnumBussinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if(userModel == null){
            throw new BusinessException(EnumBussinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }
        String promoToken = promoService.generateSecondKillToken(promoId,itemId,userModel.getId());
        if(promoToken == null){
            throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"生成令牌失败");
        }
        return CommonReturnType.create(promoToken);
    }

    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId", required = false) Integer promoId,
                                        @RequestParam(name="promoToken",required = false)String promoToken) throws BusinessException, JsonProcessingException {
        String token=httpServletRequest.getParameterMap().get("token")[0];
        if (StringUtils.isEmpty(token)){
            throw  new BusinessException(EnumBussinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }
        UserModel userModel=(UserModel) redisTemplate.opsForValue().get(token);
        if (userModel == null){
            throw  new BusinessException(EnumBussinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }

        if(redisTemplate.hasKey("promo_item_stock_invalid_"+itemId)){
            throw new BusinessException(EnumBussinessError.STOCK_NOT_ENOUGH);
        }

//        if(promoId != null){
//            String inRedisPromoToken = (String) redisTemplate.opsForValue().get("promo_token_"+promoId+"_userid_"+userModel.getId()+"_itemid_"+itemId);
//            if(inRedisPromoToken == null){
//                throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"秒杀令牌校验失败");
//            }
//            if(!org.apache.commons.lang3.StringUtils.equals(promoToken,inRedisPromoToken)){
//                throw new BusinessException(EnumBussinessError.PARAMETER_VALIDATION_ERROR,"秒杀令牌校验失败");
//            }
//        }

        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result){
            throw new BusinessException(EnumBussinessError.STOCK_NOT_ENOUGH);
        }

        Future<Object> future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                String stockLogId = itemService.initStockLog(itemId,amount);
                if(!producer.transactionAsyncReduceStock(userModel.getId(),itemId,promoId,amount,stockLogId)){
                    itemService.increaseStock(itemId,amount);
                    throw new BusinessException(EnumBussinessError.UNKNOWN_ERROR,"下单失败");
                }
                return null;
            }
        });

//        尝试直接返回不用等待真是返回结果
//        try {
//            future.get();
//            System.out.println("get message return in main out");
//        } catch (InterruptedException e) {
//            throw new BusinessException(EnumBussinessError.UNKNOWN_ERROR);
//        } catch (ExecutionException e) {
//            throw new BusinessException(EnumBussinessError.UNKNOWN_ERROR);
//        }
        return CommonReturnType.create(null);
    }



    //生成验证码
    @RequestMapping(value = "/generateverifycode",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void generateverifycode(HttpServletResponse response) throws BusinessException, IOException {
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if(org.springframework.util.StringUtils.isEmpty(token)){
            throw new BusinessException(EnumBussinessError.USER_NOT_LOGIN,"用户还未登陆，不能生成验证码");
        }
        System.out.println("token");
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if(userModel == null){
            throw new BusinessException(EnumBussinessError.USER_NOT_LOGIN,"用户还未登陆，不能生成验证码");
        }
        Map<String,Object> map = CodeUtil.generateCodeAndPic();
        redisTemplate.opsForValue().set("verify_code_"+userModel.getId(),map.get("code"));
        redisTemplate.expire("verify_code_"+userModel.getId(),10,TimeUnit.MINUTES);
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", response.getOutputStream());
    }

    @RequestMapping(value = "/testParamsNotRequire",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public CommonReturnType testParamsNotRequire(@RequestParam(name="promoToken",required = false)String promoToken) throws BusinessException, JsonProcessingException {
        System.out.println(promoToken);
        Map<String,Object>map=new HashMap<>();
        map.put("prompToken",promoToken);
        return CommonReturnType.create(map);
    }
}

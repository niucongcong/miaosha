package com.miaoshaproject.miaosha.Error;


import com.miaoshaproject.miaosha.Controllers.UserController;
import com.miaoshaproject.miaosha.Response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception exception){
        logger.info("exception->>>>>"+exception.getMessage());
        HashMap<String,Object> response=new HashMap<>();
        if (exception instanceof BusinessException){
            BusinessException businessException=(BusinessException)exception;
            response.put("errCode", businessException.getErrorCode());
            response.put("errMessage", businessException.getErrorMessage());
        }else if(exception instanceof ServletRequestBindingException){
            response.put("errCode",EnumBussinessError.UNKNOWN_ERROR.getErrorCode());
            response.put("errMsg","url绑定路由问题");
        }else if(exception instanceof NoHandlerFoundException){
            response.put("errCode",EnumBussinessError.UNKNOWN_ERROR.getErrorCode());
            response.put("errMsg","没有找到对应的访问路径");
        }
        else{
            response.put("errCode", EnumBussinessError.UNKNOWN_ERROR.getErrorCode());
            response.put("errMessage", EnumBussinessError.UNKNOWN_ERROR.getErrorMessage());
        }
        return CommonReturnType.create(response, "fail");
    }
}

package com.miaoshaproject.miaosha.Controllers;

import com.miaoshaproject.miaosha.Error.BusinessException;
import com.miaoshaproject.miaosha.Error.EnumBussinessError;
import com.miaoshaproject.miaosha.Response.CommonReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    private static Logger logger= LoggerFactory.getLogger(BaseController.class);

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

}

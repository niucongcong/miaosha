package org.example.Controller;

import org.example.Response.CommonReturnType;
import org.example.Response.Model.OrderModel;
import org.example.Service.ServiceImp.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller("order")
@RequestMapping("/order")
//单纯使用@CrossOrigin并没有解决跨域，需要添加后面的allowCredentials = "true", allowedHeaders = "*"
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController {
    private static Logger logger= LoggerFactory.getLogger(OrderController.class);


    @Autowired
    private OrderService orderService;


    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listLogInfo(){
        List<OrderModel> data = orderService.getAllItem();
        return CommonReturnType.create(data);
    }

}

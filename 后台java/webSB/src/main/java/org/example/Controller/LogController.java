package org.example.Controller;


import org.example.Response.CommonReturnType;
import org.example.Response.Model.LogInfoModel;
import org.example.Service.ServiceImp.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("log")
@RequestMapping("/log")
//单纯使用@CrossOrigin并没有解决跨域，需要添加后面的allowCredentials = "true", allowedHeaders = "*"
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class LogController {

    private static Logger logger= LoggerFactory.getLogger(ItemController.class);


    @Autowired
    private LogService logService;


    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listLogInfo(){
        List<LogInfoModel> data = logService.getAllItem();
        return CommonReturnType.create(data);
    }

}

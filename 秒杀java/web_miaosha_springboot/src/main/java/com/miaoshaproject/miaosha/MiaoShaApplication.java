package com.miaoshaproject.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages ={"com.miaoshaproject.miaosha"} )
@MapperScan("com.miaoshaproject.miaosha.Dao")
@RestController
public class MiaoShaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoShaApplication.class, args);
    }

}

package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages ={"org.example"} )
@MapperScan("org.example.Dao")
@RestController
public class App {
    public static void main( String[] args )
    {
        SpringApplication.run(App.class,args);
    }
}

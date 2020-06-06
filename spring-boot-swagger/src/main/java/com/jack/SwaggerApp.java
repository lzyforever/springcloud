package com.jack;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 集成Swagger
 */
@EnableSwagger2Doc
@SpringBootApplication
public class SwaggerApp {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerApp.class, args);
    }
}

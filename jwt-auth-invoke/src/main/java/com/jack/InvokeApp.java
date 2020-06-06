package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 调用JWT进行认证示例
 * 第一种使用：Feign调用前统一申请Token传递到调用服务中，加FeignConfig配置和定义FeignBasicAuthRequestInterceptor到项目中
 * 第二种使用：RestTemplate调用前统一申请Token传递到服务中，加RestTemplate配置类和TokenInterceptor到项目中
 * 第三种使用：Zuul中传递Token到路由的服务中，这个见项目jwt-auth-zuul
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class InvokeApp {
    public static void main(String[] args) {
        SpringApplication.run(InvokeApp.class, args);
    }
}

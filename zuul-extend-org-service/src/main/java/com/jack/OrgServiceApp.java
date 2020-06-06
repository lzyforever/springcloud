package com.jack;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 机构服务
 */
@EnableFeignClients
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class OrgServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(OrgServiceApp.class, args);
    }
}

package com.jack.config;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 配置类
 */
@Configuration
public class BeanConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5000,10000);
    }


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    /**
     * Feign禁用Hystrix
     */
//    @Bean
//    @Scope("prototype")
//    public Feign.Builder feginBuilder() {
//        return Feign.builder();
//    }
}

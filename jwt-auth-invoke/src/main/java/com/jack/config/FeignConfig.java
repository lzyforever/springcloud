package com.jack.config;

import com.jack.interceptor.FeignBasicAuthRequestInterceptor;
import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Feign配置类
 */
@Configuration
public class FeignConfig {

    /**
     * 设置Feign日志级别
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的Token
     * 各个微服务将Token设置到环境变量在来达到通用的目的
     */
    @Bean
    public FeignBasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }

}

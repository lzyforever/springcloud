package com.jack.config;

import com.jack.loadbalanced.MyLoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate配置
 * 和没有集成Ribbon的区别在于这里在在实例化的方法上加了@LoadBalanced的注解
 */
@Configuration
public class BeanConfiguration {
    @Bean
    //@LoadBalanced
    @MyLoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

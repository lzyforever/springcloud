package com.jack.loadbalanced;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 自定义LoadBalanced注解配置类，给RestTemplate注入拦截器
 * 维护一个@MyLoadBalanced的RestTemplate列表，在SmartInitializingSingleton中对RestTemplate进行拦截器设置
 * 其使用只需要在项目的RestTemplate注入实例时，将原来的@LoadBalanced替换成我们自定义@MyLoadBalanced注解即可
 */
@Configuration
public class MyLoadBalancedAutoConfiguration {

    @MyLoadBalanced
    @Autowired(required = false)
    private List<RestTemplate> restTemplates = Collections.emptyList();

    @Bean
    public MyLoadBalancedInterceptor myLoadBalancedInterceptor(LoadBalancerClient loadBalancer, LoadBalancerRequestFactory requestFactory) {
        return new MyLoadBalancedInterceptor(loadBalancer, requestFactory);
    }

    @Bean
    public SmartInitializingSingleton myLoadBalancedRestTemplateInitializer(LoadBalancerClient loadBalancer, LoadBalancerRequestFactory requestFactory) {
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (RestTemplate restTemplate : MyLoadBalancedAutoConfiguration.this.restTemplates) {
                    List<ClientHttpRequestInterceptor> list = new ArrayList<>(restTemplate.getInterceptors());
                    list.add(myLoadBalancedInterceptor(loadBalancer, requestFactory));
                    restTemplate.setInterceptors(list);
                }
            }
        };
    }
}

package com.jack.config;

import com.jack.jwt.filter.HttpBasicAuthorizeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器配置类
 * 在这里面注册上自定义的认证过滤器
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<HttpBasicAuthorizeFilter> filterRegistrationBean() {
        FilterRegistrationBean<HttpBasicAuthorizeFilter> registrationBean = new FilterRegistrationBean<>();
        HttpBasicAuthorizeFilter httpBasicFilter = new HttpBasicAuthorizeFilter();
        registrationBean.setFilter(httpBasicFilter);
        List<String> urlPatterns = new ArrayList<>(1);
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}

package com.jack.config;

import com.jack.filter.DebugRequestFiliter;
import com.jack.filter.ErrorFilter;
import com.jack.filter.IpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置类
 */
@Configuration
public class FilterConfig {

    /**
     * 注册自定义的过滤器，进行配置才能生效
     */
    @Bean
    public IpFilter ipFilter() {
        return new IpFilter();
    }

    /**
     * 注册自定义异常过滤器，进行配置才能生效
     */
    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    /**
     * 注册自定义信息输出过滤器，进行配置和能生效
     */
    @Bean
    public DebugRequestFiliter debugRequestFiliter() {
        return new DebugRequestFiliter();
    }
}

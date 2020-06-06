package com.jack.config;

import com.jack.filter.AuthHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Zuul过滤器配置
 */
@Configuration
public class FilterConfig {
    @Bean
    public AuthHeaderFilter authHeaderFilter() {
        return new AuthHeaderFilter();
    }
}

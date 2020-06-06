package com.jack.config;

import com.jack.filter.AuthFilter;
import com.jack.filter.ClusterLimitFilter;
import com.jack.filter.LimitFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置类
 */
@Configuration
public class FilterConfig {

    @Bean
    public AuthFilter authFilter() {
        return new AuthFilter();
    }

//    /**
//     * 单节点限流过滤器
//     */
//    @Bean
//    public LimitFilter limitFilter() {
//        return new LimitFilter();
//    }

    /**
     * 集群限流过滤器
     */
    @Bean
    public ClusterLimitFilter clusterLimitFilter() {
        return new ClusterLimitFilter();
    }
}

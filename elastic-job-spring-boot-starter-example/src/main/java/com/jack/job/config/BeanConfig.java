package com.jack.job.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 配置类
 */
@Configuration
public class BeanConfig {

    /**
     * 任务执行事件数据源
     * @return
     */
    @Bean("datasource")
    @ConfigurationProperties("spring.datasource.druid.log")
    public DataSource dataSourceTwo() {
        return DruidDataSourceBuilder.create().build();
    }
}

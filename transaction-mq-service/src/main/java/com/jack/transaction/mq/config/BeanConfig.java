package com.jack.transaction.mq.config;

import com.jack.jdbc.ExtendJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
public class BeanConfig {
    @Bean(autowire = Autowire.BY_NAME)
    public ExtendJdbcTemplate extendJdbcTemplate() {
        return new ExtendJdbcTemplate("com.jack.transaction.mq.entity");
    }
}

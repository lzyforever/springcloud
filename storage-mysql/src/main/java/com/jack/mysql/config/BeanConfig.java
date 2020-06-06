package com.jack.mysql.config;

import com.jack.jdbc.ExtendJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 */
@Configuration
public class BeanConfig {
    /**
     * 如果是spring boot项目可以使用bean的方式配置
     */
    @Bean(autowire = Autowire.BY_NAME)
    public ExtendJdbcTemplate extendJdbcTemplate() {
        // ExtendJdbcTemplate构造方法中传的com.jack.mysql.entity是你数据表对应的PO实体类所在的包路径
        // 推荐放一个包下，如果在多个包下可以配置多个包的路径
        return new ExtendJdbcTemplate("com.jack.mysql.entity");
    }
}

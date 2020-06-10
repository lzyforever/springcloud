package com.jack.elasticjob.annotation;

import com.jack.elasticjob.autoconfigure.JobParserAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启ElasticJob注解
 * 在SpringBoot启动类上添加此注解开启自动配置
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({JobParserAutoConfiguration.class})
public @interface EnableElasticJob {
}

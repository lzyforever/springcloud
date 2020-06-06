package com.jack.apilimit.annotation;

import java.lang.annotation.*;

/**
 * API访问速度限制注解
 * 限制的速度值在Apollo配置中通过key关联
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiRateLimit {
    /** Apollo配置中的key */
    String confKey();
}

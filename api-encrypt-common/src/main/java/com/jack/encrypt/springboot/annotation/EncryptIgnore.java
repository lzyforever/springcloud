package com.jack.encrypt.springboot.annotation;

import java.lang.annotation.*;

/**
 * 忽略加密注解
 * 加了此注解的接口将不进行数据加密操作
 * 适用于全局开启加密操作，但是想忽略某些接口的场景
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptIgnore {
    String value() default "";
}

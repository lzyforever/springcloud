package com.jack.encrypt.springboot.annotation;

import java.lang.annotation.*;

/**
 * 忽略解密注解
 * 加了些注解的接口将不进行数据解密操作
 * 适用于全局开启加解密操作，但是想忽略某些接口的场景
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecryptIgnore {
    String value() default "";
}

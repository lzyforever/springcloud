package com.jack.encrypt.aonnotation;

import java.lang.annotation.*;

/**
 * 加密注解
 * 使用此注解的接口将进行数据加密操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
}

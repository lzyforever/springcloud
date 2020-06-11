package com.jack.encrypt.aonnotation;

import java.lang.annotation.*;

/**
 * 解密注解
 * 使用此注解的接口将进行数据解密操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypt {
}

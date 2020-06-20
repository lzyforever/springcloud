package com.jack.encrypt.springboot.annotation;

import java.lang.annotation.*;

/**
 * 解密注解
 * 加了此注解的接口将进行数据解密操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decrypt {
    String value() default "";

    /**
     * URL参数解密，多个参数用应该用逗号隔开，比如：name,age
     *
     * @return 解密参数信息
     */
    String decryptParam() default "";
}

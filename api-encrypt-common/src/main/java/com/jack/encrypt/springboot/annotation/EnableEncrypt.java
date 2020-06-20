package com.jack.encrypt.springboot.annotation;

import com.jack.encrypt.springboot.autoconfigure.EncryptAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用加密Starter
 * 在Spring boot启动类上添加此注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EncryptAutoConfiguration.class)
public @interface EnableEncrypt {
}

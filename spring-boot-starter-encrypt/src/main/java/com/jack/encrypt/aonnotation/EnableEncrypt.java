package com.jack.encrypt.aonnotation;

import com.jack.encrypt.autoconfigure.EncryptAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用加密Starter
 * 在Spring Boot启动类上加上此注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EncryptAutoConfiguration.class)
public @interface EnableEncrypt {
}

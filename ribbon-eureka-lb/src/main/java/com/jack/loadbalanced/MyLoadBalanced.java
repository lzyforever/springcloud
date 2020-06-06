package com.jack.loadbalanced;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * 自定义一个@LoadBalanced注解，模拟LoadBalanced的原理
 * 将@LoadBalanced注解里面的定义COPY过来就可以了
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Qualifier
public @interface MyLoadBalanced {
}

package com.jack.apilimit.aspect;

import com.jack.apilimit.annotation.ApiRateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

/**
 * 具体API并发控制
 */
@Aspect
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ApiLimitAspect {

    public static Map<String, Semaphore> semaphoreMap = new ConcurrentHashMap<>();

    @Around("execution(* com.jack.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        Semaphore semap = null;
        Class<?> clazz = joinPoint.getTarget().getClass();
        String key = getRateLimitKey(clazz, joinPoint.getSignature().getName());
        if (key != null) {
            semap = semaphoreMap.get(key);
            if (semap == null) {
                semap = semaphoreMap.get("open.api.defaultLimit");
            }
        } else {
            semap = semaphoreMap.get("open.api.defaultLimit");
        }
        try {
            semap.acquire();
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            semap.release();
        }
        return result;
    }

    /**
     * 获取当前访问的目标对象以及方法名称
     */
    private String getRateLimitKey(Class<?> clazz, String methodName) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                if (method.isAnnotationPresent(ApiRateLimit.class)) {
                    return method.getAnnotation(ApiRateLimit.class).confKey();
                }
            }
        }
        return null;
    }
}

package com.jack.apilimit.listener;

import com.jack.apilimit.annotation.ApiRateLimit;
import com.jack.apilimit.aspect.ApiLimitAspect;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 初始化需要进行并发限制的API
 */
@Component
public class InitApiLimitRateListener implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Environment environment = ctx.getEnvironment();
        String defaultLimit = environment.getProperty("open.api.defaultLimit");
        Object rate = defaultLimit == null ? 100 : defaultLimit;
        ApiLimitAspect.semaphoreMap.put("open.api.defaultLimit", new Semaphore(Integer.parseInt(rate.toString())));
        Map<String, Object> beanMap = ctx.getBeansWithAnnotation(RestController.class);
        Set<String> keys = beanMap.keySet();
        for (String key : keys) {
            Class<?> clazz = beanMap.get(key).getClass();
            String fullName = beanMap.get(key).getClass().getName();
            if (fullName.contains("EnhancerBySpringCGLIB") || fullName.contains("$$")) {
                fullName = fullName.substring(0, fullName.indexOf("$$"));
                try {
                    clazz = Class.forName(fullName);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            Method[] methods = clazz.getMethods();
            for (Method method: methods) {
                if (method.isAnnotationPresent(ApiRateLimit.class)) {
                    String conKey = method.getAnnotation(ApiRateLimit.class).confKey();
                    if (environment.getProperty(conKey) != null) {
                        int limit = Integer.parseInt(environment.getProperty(conKey));
                        ApiLimitAspect.semaphoreMap.put(conKey, new Semaphore(limit));
                    }
                }
            }
        }
    }
}

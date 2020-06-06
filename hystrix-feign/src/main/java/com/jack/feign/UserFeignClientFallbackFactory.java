package com.jack.feign;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 回退的定义，用于UserFeignClient的fallbackFactory
 * 优点：可以记录回退的日志
 */
@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    private Logger logger = LoggerFactory.getLogger(UserFeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable throwable) {
        logger.error("UserFeignClient回退: ", throwable);
        return new UserFeignClient() {
            @Override
            public String hello() {
                return "fail";
            }
        };
    }
}

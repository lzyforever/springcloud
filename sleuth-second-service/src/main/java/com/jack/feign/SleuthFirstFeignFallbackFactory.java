package com.jack.feign;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SleuthFirstFeignFallbackFactory implements FallbackFactory<SleuthFirstFeign> {
    private Logger logger = LoggerFactory.getLogger(SleuthFirstFeignFallbackFactory.class);

    @Override
    public SleuthFirstFeign create(Throwable throwable) {
        logger.error("SleuthFirstFeign 回退: " + throwable);
        return () -> "call fail";
    }

}

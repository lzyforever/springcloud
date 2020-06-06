package com.jack.feign;

import com.jack.config.BeanConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "sleuth-frist-service", configuration = BeanConfiguration.class, fallbackFactory = SleuthFirstFeignFallbackFactory.class)
public interface SleuthFirstFeign {
    @GetMapping("/test")
    String test();
}

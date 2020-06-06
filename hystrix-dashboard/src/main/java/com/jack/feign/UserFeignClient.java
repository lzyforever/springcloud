package com.jack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户Feign客户端
 */
@FeignClient(value = "service-provider", fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {
    @GetMapping("/user/hello")
    String sayHi();
}

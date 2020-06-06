package com.jack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户Feign调用
 */
@FeignClient(value = "zuul-extend-user-service")
public interface UserRemoteClient {
    @GetMapping("/user/hello")
    String hello();
}

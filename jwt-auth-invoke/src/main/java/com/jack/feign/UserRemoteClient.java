package com.jack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户API调用客户端
 */
@FeignClient(value = "jwt-user-service")
public interface UserRemoteClient {
    @GetMapping("/user/hello")
    String hello();
}

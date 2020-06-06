package com.jack.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 用户Feign客户端
 * fallback和fallbackFactory的区别在于能不能记录异常信息
 */
// 指定fallback的类，
//@FeignClient(value = "service-provider", fallback = UserFeignClientFallback.class)
// 指定fallbackFactory，可以记录回退异常信息，保存日志信息
@FeignClient(value = "service-provider", fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {
    @GetMapping("/user/hello")
    String hello();
}

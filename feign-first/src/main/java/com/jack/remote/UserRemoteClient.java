package com.jack.remote;

import com.jack.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 定义一个Feign客户端，以接口形式存在
 * 加上@FeignClient注解，将标识当前是一个Feign的客户端
 * value属性是对应的服务名称，也就是你需要调用哪个服务中的接口，这里的value就写哪个服务的名称
 * configuration属性是指定的Feign配置类
 */
@FeignClient(value = "service-provider", configuration = FeignConfiguration.class)
public interface UserRemoteClient {
    /**
     * 定义接口方法时，直接复制接口定义即可，当然还有另一种做法，就是将接口单独抽出来定义，
     * 然后在Controller中实现接口。在调用的客户端中也实现了接口，从而达到接口共用的目的。
     */
    @GetMapping("/user/hello")
    String hello();
}

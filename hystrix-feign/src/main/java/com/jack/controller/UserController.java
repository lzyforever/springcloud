package com.jack.controller;

import com.jack.feign.UserFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试Hystrix
 */
@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 在接口方法上添加 @HystrixCommand注解
     * 作用：用于指定依赖服务调用延迟时调用的方法
     * 效果：当调用失败触发熔断时会用使用fallbackMethod指定的defaultCallHello方法来返回具体的内容
     *
     * 也可以在@HystrixCommand注解里面配置commandProperties，进行策略配置
     */
    @GetMapping("/callHello")
    @HystrixCommand(fallbackMethod = "defaultCallHello", commandProperties = {
            @HystrixProperty(
                    name="execution.isolation.strategy",
                    value="THREAD"
            )
    })
    public String callHello() {
        String result = restTemplate.getForObject("http://localhost:8083/user/hello", String.class);
        System.out.println(result);
        return result;
    }

    public String defaultCallHello() {
        return "fail";
    }

    @GetMapping("/sayHi")
    public String sayHi() {
        String result = userFeignClient.hello();
        System.out.println(result);
        return result;
    }
}

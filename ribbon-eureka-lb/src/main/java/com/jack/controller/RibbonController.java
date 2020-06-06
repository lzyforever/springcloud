package com.jack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ribbon API使用
 * 通过LoadBalancerClient来获取一些信息
 */
@RestController
public class RibbonController {
    @Autowired
    private LoadBalancerClient loadBalancer;

    @GetMapping("/choose/{name}")
    public Object chooseUrl(@PathVariable("name") String name) {
        ServiceInstance instance = loadBalancer.choose(name);
        return instance;
    }
}

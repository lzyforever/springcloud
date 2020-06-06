package com.jack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 除了使用Eureka Client来获取信息，还可以通过DiscoveryClient来获取信息
 */
@RestController
public class DiscoveryController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/infos2")
    public Object serviceUrl() {
        return discoveryClient.getInstances("service-provider");
    }

}

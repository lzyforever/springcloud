package com.jack.controller;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过EurekaClient获取Eureka中的元数据信息
 */
@RestController
public class EurekaController {

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping("/infos")
    public Object serviceUrl() {
        return eurekaClient.getInstancesByVipAddress("service-provider", false);
    }

}

package com.jack.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * 通过代码来配置Ribbon
 * 此时，就不需要在application.yml里面进行配置
 */
@RibbonClient(name = "ribbon-config", configuration = BeanConfiguration.class)
public class RibbonClientConfig {
}

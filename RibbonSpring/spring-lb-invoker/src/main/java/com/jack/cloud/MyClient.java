package com.jack.cloud;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * 通过注解@RibbonClient的方式进行配置
 * 
 * 也可以通过配置文件的方式进行配置：客户端名称.命名空间.配置项=自定义规则
 * spring-lb-provider.ribbon.NFLoadBalancerRuleClassName=com.jack.cloud.MyRule
 * 如需要覆盖默认的default则将替换为：
 * default.ribbon.NFLoadBalancerRuleClassName=com.jack.cloud.MyRule即可
 * 
 */
/*@RibbonClient(name="spring-lb-provider", configuration=MyConfig.class)*/
public class MyClient {
	
}

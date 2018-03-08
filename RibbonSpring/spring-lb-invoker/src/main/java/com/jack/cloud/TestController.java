package com.jack.cloud;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

@Controller
@Configuration
public class TestController {
	
	/**
	 * 使用SpringWeb提供的RestTemplate，它是一个Rest调用客户端工具类
	 * Ribbon与Cloud对此类进行了扩展
	 * 使用@LoadBalanced的注解修饰，就有负载均衡的功能了
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@GetMapping("/router")
	@ResponseBody
	public String router() {
		RestTemplate template = getRestTemplate();
		return template.getForObject("http://spring-lb-provider/call/1", String.class);
	}
	
	
	//-----------------------------------------------------------
	/**
	 * 可以通过SpringCloud封装的Ribbon来调用其API
	 * 在此，注入SpringCloud封装好的Ribbon负载均衡客户端
	 */
	@Autowired
	private LoadBalancerClient client;
	
	/**
	 * 一个服务可以布署多个实例，它们在不同的服务器上面跑，通过loadbalance client来帮我们选择一个服务器来调用
	 * 只不过它把服务器封装成一个服务实例，因为对于SpringCloud来说，它针对服务
	 */
	@GetMapping("/lb")
	@ResponseBody
	public ServiceInstance lb() {
		return client.choose("spring-lb-provider");
	}
	
	//-----------------------------------------------------------
	
	/**
	 * 查看一下SpringCloud封装的Ribbon有哪些默认的配置
	 */
	@Autowired
	private SpringClientFactory factory;
	
	@GetMapping("/factory")
	@ResponseBody
	public String factory() {
		String result = "";
		ILoadBalancer lBalancer = factory.getLoadBalancer("default");
		result += "loadbalance: " + lBalancer.getClass().getName();
		
		result += " Rule: " + ((ZoneAwareLoadBalancer) lBalancer).getRule().getClass().getName();
		
		ILoadBalancer lBalancer2 = factory.getLoadBalancer("spring-lb-provider");
		
		result += "<br/> Custom loadbalance: " + lBalancer2.getClass().getName();
		result += " Custom Rule: " + ((ZoneAwareLoadBalancer) lBalancer2).getRule().getClass().getName();
		
		return result;
	}
	
}

package com.jack.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;


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
		return template.getForObject("http://FIRST-EUREKA-PROVIDER/call/1", String.class);
	}
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	/**
	 * 输出服务器服务列表
	 */
	@GetMapping("/list")
	@ResponseBody
	public String serviceCount() {
		List<String> names = discoveryClient.getServices(); // 获取所有服务
		String result = "";
		for (String serviceId : names) {
			List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
			result += serviceId + " : " + instances.size() + "<br/>";
		}
		
		return result;
	}
	
	/**
	 * 获取配置在服务提供者客户端(application.yml)当中的信息
	 */
	@GetMapping("/meta")
	@ResponseBody
	public String getMetaData() {
		List<ServiceInstance> instances = discoveryClient.getInstances("ek-provider");
		String result = "";
		for (ServiceInstance instance : instances) {
			
			result += "host: " + instance.getHost() 
			+ " -- port: " + instance.getPort() 
			+ " -- uri: " + instance.getUri()
			+ " -- companyName: " + instance.getMetadata().get("company-name")
			+ " <br/>";
		}
		
		return result;
	}
}

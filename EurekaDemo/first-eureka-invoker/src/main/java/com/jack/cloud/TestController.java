package com.jack.cloud;

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
	
}

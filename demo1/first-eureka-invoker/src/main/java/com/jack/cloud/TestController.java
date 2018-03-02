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
	 * ʹ��SpringWeb�ṩ��RestTemplate������һ��Rest���ÿͻ��˹�����
	 * Ribbon��Cloud�Դ����������չ
	 * ʹ��@LoadBalanced��ע�����Σ����и��ؾ���Ĺ�����
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

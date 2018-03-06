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
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	/**
	 * ��������������б�
	 */
	@GetMapping("/list")
	@ResponseBody
	public String serviceCount() {
		List<String> names = discoveryClient.getServices(); // ��ȡ���з���
		String result = "";
		for (String serviceId : names) {
			List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
			result += serviceId + " : " + instances.size() + "<br/>";
		}
		
		return result;
	}
	
	/**
	 * ��ȡ�����ڷ����ṩ�߿ͻ���(application.yml)���е���Ϣ
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

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
		return template.getForObject("http://spring-lb-provider/call/1", String.class);
	}
	
	
	//-----------------------------------------------------------
	/**
	 * ����ͨ��SpringCloud��װ��Ribbon��������API
	 * �ڴˣ�ע��SpringCloud��װ�õ�Ribbon���ؾ���ͻ���
	 */
	@Autowired
	private LoadBalancerClient client;
	
	/**
	 * һ��������Բ�����ʵ���������ڲ�ͬ�ķ����������ܣ�ͨ��loadbalance client��������ѡ��һ��������������
	 * ֻ�������ѷ�������װ��һ������ʵ������Ϊ����SpringCloud��˵������Է���
	 */
	@GetMapping("/lb")
	@ResponseBody
	public ServiceInstance lb() {
		return client.choose("spring-lb-provider");
	}
	
	//-----------------------------------------------------------
	
	/**
	 * �鿴һ��SpringCloud��װ��Ribbon����ЩĬ�ϵ�����
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

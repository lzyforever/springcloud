package com.jack.cloud;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {
	
	@Autowired(required = false)
	@MyLoadBalanced
	private List<RestTemplate> tpls = Collections.emptyList();
	
	@Autowired
	private MyInterceptor myInterceptor;
	
	/**
	 * 初始化方法
	 */
	@Bean
	public SmartInitializingSingleton lbInitializingSingleton() {
		return new SmartInitializingSingleton() {
			
			@Override
			public void afterSingletonsInstantiated() {
				System.out.println(tpls.size());
				for (RestTemplate template : tpls) {
					List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
					interceptors.add(new MyInterceptor());
					template.setInterceptors(interceptors);
				}
			}
		};
	}

}

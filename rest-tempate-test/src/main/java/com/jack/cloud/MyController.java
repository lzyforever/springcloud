package com.jack.cloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Configuration
public class MyController {

	@Bean
	@MyLoadBalanced
	public RestTemplate getTempA() {
		return new RestTemplate();
	}
	
	@GetMapping("/call")
	public String call() {
		RestTemplate template = getTempA();
		return template.getForObject("http://hello-service/call", String.class);
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "hello world!";
	}
}

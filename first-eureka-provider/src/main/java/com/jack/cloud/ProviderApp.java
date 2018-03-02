package com.jack.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ProviderApp {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ProviderApp.class).web(true).run(args);
	}
}

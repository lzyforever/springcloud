package com.jack.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class InvokerApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(InvokerApp.class).web(true).run(args);
	}

}

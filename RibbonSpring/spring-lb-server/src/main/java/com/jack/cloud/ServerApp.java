package com.jack.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServerApp {
	public static void main(String[] args) {
		new SpringApplicationBuilder(ServerApp.class).web(true).run(args);
	}
}

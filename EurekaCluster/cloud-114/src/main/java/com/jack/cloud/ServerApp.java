package com.jack.cloud;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServerApp {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String profiles = scanner.nextLine();
		new SpringApplicationBuilder(ServerApp.class).profiles(profiles).run(args);
	}
}

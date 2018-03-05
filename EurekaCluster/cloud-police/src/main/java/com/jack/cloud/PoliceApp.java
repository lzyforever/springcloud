package com.jack.cloud;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PoliceApp {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		// 需要两个实例，则需要两个端口，读取控制台端口输入
		String port = scanner.nextLine();
		new SpringApplicationBuilder(PoliceApp.class).properties("server.port=" + port).run(args);
	}
}

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
		// ��Ҫ����ʵ��������Ҫ�����˿ڣ���ȡ����̨�˿�����
		String port = scanner.nextLine();
		new SpringApplicationBuilder(PoliceApp.class).properties("server.port=" + port).run(args);
	}
}

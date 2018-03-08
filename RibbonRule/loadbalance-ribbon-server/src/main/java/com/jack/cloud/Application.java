package com.jack.cloud;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String port = scanner.nextLine();
		new SpringApplicationBuilder(Application.class).properties("server.port="+port).run(args);
	}
}

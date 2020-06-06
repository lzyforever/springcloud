package com.jack;

import com.jack.config.EnableUserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableUserClient
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}

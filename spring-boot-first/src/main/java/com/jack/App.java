package com.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * ************************************
 * Copyright 四川中疗网络科技有限公司 *
 * ************************************
 * Description: TODO
 *
 * @author luozy
 * @version cmnt-cloud V1.0
 * @date 2020/2/17 15:28
 */
@EnableAsync
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

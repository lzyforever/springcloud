package com.jack.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MyEventApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MyEventApp.class, args);

        // 方法1：通过实现Listener接口，再使用ApplicationContext来发布事件
        context.getBean(MyEventPublisher.class).publish("Hello World!");

        // 方法2：通过在方法上添加注解，再使用ApplicationEventPublisher够发布事件
        context.getBean(MyEventPublisher2.class).publish("Hello Jack");
    }


}

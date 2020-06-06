package com.jack.transaction.mq.task;

import com.jack.transaction.mq.task.task.ProcessMessageTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * 事务消息发送系统启动类
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.jack.transaction.mq.client")
@SpringBootApplication
public class TransactionMqTaskApp {
    private static final Logger logger = LoggerFactory.getLogger(TransactionMqTaskApp.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TransactionMqTaskApp.class);
        ConfigurableApplicationContext context = application.run(args);
        try {
            ProcessMessageTask task = context.getBean(ProcessMessageTask.class);
            task.start();
            new CountDownLatch(1).await();
        } catch (InterruptedException e) {
            logger.error("项目启动异常");
        }
    }
}

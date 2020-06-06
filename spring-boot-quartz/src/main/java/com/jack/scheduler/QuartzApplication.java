package com.jack.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * quartz定时任务分布式节点demo
 *
 * 如果两个节点同时启动，哪个节点先把节点信息注册到数据库就获得了优先执行权
 * 如果关闭其中的一个，那么另一个会自动接管数据库内的任务，完成任务执行的自动漂移
 *
 */
@SpringBootApplication
public class QuartzApplication {
    private static Logger logger = LoggerFactory.getLogger(QuartzApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
        logger.info("定时任务分布式节点 - spring-boot-quartz 已启动");
    }
}

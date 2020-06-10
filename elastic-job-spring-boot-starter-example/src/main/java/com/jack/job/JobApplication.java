package com.jack.job;

import com.jack.elasticjob.annotation.EnableElasticJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Elastic Job Spring boot 集成示例
 */
@SpringBootApplication
@EnableElasticJob
//开启动态任务添加API
@ComponentScan(basePackages = {"com.jack"})
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}

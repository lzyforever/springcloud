package com.jack.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Sleuth异步线程池配置
 * Sleuth对异步任务也是支持的，使用@Async开启一个异步任务后，Sleuth会为这个调用新创建一个Span
 * 如果自定义了异步任务的线程池，会导致无法新创建一个Span，这就需要使用Sleuth提供的LazyTraceExecutor来包装一下
 * 如果在getAsyncExecutor方法中直接返回Executor就不会新建Span，也就不会有在任务上定义的write-msg标签出现在zipkin中
 */
@Configuration
@EnableAutoConfiguration
public class CustomExecutorConfig extends AsyncConfigurerSupport {

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(7);
        executor.setMaxPoolSize(42);
        executor.setQueueCapacity(11);
        executor.setThreadNamePrefix("jack-");
        executor.initialize();
        return new LazyTraceExecutor(this.beanFactory, executor);
    }
}

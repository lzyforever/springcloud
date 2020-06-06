package com.jack.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步执行
 * 异步调用就是不用等待结果的返回就执行后面的逻辑；
 * 同步调用则需要等待结果再执行后面的逻辑
 * 通常我们使用异步操作时都会创建一个线程执行一段逻辑，然后把这个线程丢到线程池中去执行
 */
@Configuration
public class AsyncTaskExecutePool implements AsyncConfigurer {

    private Logger logger = LoggerFactory.getLogger(AsyncTaskExecutePool.class);

    @Autowired
    private TaskThreadPoolConfig config;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());
        executor.setMaxPoolSize(config.getMaxPoolSize());
        executor.setQueueCapacity(config.getQueueCapacity());
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
        executor.setThreadNamePrefix(config.getThreadNamePrefix());
        // 任务的拒绝策略，分为两种
        // 一种是：AbortPolicy，直接抛出java.util.concurrent.RejectExecutionException异常
        // 二种是：CallerRunsPolicy，主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，这样的好处是降低向线程池中添加任务的速度
        // 建议使用第二种策略，因为当队列中的任务满了之后，如果第一种，产生异常那么这个任务就会被抛弃，如果是第二种，则会用主线程去执行，也就是
        // 同步执行，至少这个任务不会被丢弃掉。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            logger.error("==========================" + throwable.getMessage() + "=======================", throwable);
            logger.error("exception method:" + method.getName());
        };
    }
}

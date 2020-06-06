package com.jack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * 线程隔离策略配置
 */
public class MyFifthHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public MyFifthHystrixCommand(String name) {
        super(HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("MyGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withMaxQueueSize(100)
                        .withMaximumSize(100)
                )
        );
        this.name = name;
    }

    protected String run() throws Exception {
        System.out.println("get data");
        return this.name + " : " + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "失败了...";
    }
}

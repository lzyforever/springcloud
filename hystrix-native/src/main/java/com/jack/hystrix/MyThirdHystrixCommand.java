package com.jack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * 构建一个HystrixCommand对象，并进行信号量策略配置
 *
 */
public class MyThirdHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public MyThirdHystrixCommand(String name) {
        super(HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("MyGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(
                                HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE
                        )
                )
        );
        this.name = name;
    }

    protected String run() throws Exception {
        return this.name + " : " + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "失败了...";
    }
}

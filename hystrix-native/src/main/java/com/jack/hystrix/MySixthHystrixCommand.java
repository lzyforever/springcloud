package com.jack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * 构建一个HystrixCommand对象
 * 使用Hystrix中为我们提供的方法级别的缓存，通过重写getCacheKey来判断是否返回缓存的数据
 * getCacheKey可以根据参数来生成，这样，同样的参数就可以都用到缓存了
 *
 * 缓存的处理取决了上下文，记得要调用一下这个初始化请求上下文的方法，不然会报错
 * HystrixRequestContext.initializeContext()
 *
 */
public class MySixthHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public MySixthHystrixCommand(String name) {

        super(HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("MyGroup"))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationStrategy(
                                        HystrixCommandProperties.ExecutionIsolationStrategy.THREAD
                                )
                ).andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withCoreSize(10)
                                .withMaxQueueSize(100)
                                .withMaximumSize(100)
                )
        );


        this.name = name;
    }

    @Override
    protected String run() {
        System.err.println("get data");
        return this.name + " : " + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "失败了";
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(this.name);
    }

}

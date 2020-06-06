package com.jack.test;

import com.jack.hystrix.MySixthHystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 测试结果缓存
 */
public class MySixthHystrixTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 记得要调用一下这个初始化请求上下文的方法，不然会报错！！！
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        String result = new MySixthHystrixCommand("luozy").execute();
        // luozy : hystrix-MyGroup-1
        System.out.println(result);

        Future<String> future = new MySixthHystrixCommand("luozy").queue();
        // luozy : hystrix-MyGroup-1
        System.out.println(future.get());

        // 两次的结果是一样的，在run()方法输出的get data只有一次，说明缓存生效了

        hystrixRequestContext.shutdown();

    }
}

package com.jack.test;

import com.jack.hystrix.MySeventhHystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 测试清除结果缓存
 */
public class MySeventhHystrixTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 记得要调用一下这个初始化请求上下文的方法，不然会报错！！！
        HystrixRequestContext hystrixRequestContext = HystrixRequestContext.initializeContext();
        String result = new MySeventhHystrixCommand("luozy").execute();
        System.out.println(result);

        MySeventhHystrixCommand.flushCache("luozy");

        Future<String> future = new MySeventhHystrixCommand("luozy").queue();
        System.out.println(future.get());

        // 在调用flushCache之后，get data就出现了，并而线程名称也变了，说明缓存被清理了

        hystrixRequestContext.shutdown();

    }
}

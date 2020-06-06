package com.jack.test;

import com.jack.hystrix.MyEighthHystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.concurrent.Future;

/**
 * 测试合并请求
 * 通过MyEighthHystrixCommand创建两个执行任务，按照正常的逻辑肯定是分别执行这两个任务，但是
 * 通过HystrixCollapser可以将这多个任务合并到一起执行。
 */
public class MyEigthHystrixTest {
    public static void main(String[] args) throws Exception{
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<String> f1 = new MyEighthHystrixCommand("luozy").queue();
        Future<String> f2 = new MyEighthHystrixCommand("jack").queue();
        System.out.println("luozy: " + f1.get());
        System.out.println("jack: " + f2.get());
        context.shutdown();

        //真正执行请求....
        //luozy: 返回的结果：luozy
        //jack: 返回的结果：jack

        // 打出真正执行请求的这句话只有一次，确实是两个任务一起执行的
    }
}

package com.jack.test;

import com.jack.hystrix.MyFifthHystrixCommand;

import java.util.concurrent.Future;

/**
 * 测试线程隔离策略配置
 */
public class MyFifthHystrixTest {
    public static void main(String[] args) throws Exception{
        String result = new MyFifthHystrixCommand("luozy").execute();
        System.out.println("执行结果是：" + result);
        Future<String> future = new MyFifthHystrixCommand("jack").queue();
        System.out.println("执行结果是：" + future.get());
    }
}

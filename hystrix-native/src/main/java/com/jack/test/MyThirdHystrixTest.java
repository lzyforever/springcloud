package com.jack.test;

import com.jack.hystrix.MyThirdHystrixCommand;

import java.util.concurrent.Future;

/**
 * 测试信息量策略配置
 */
public class MyThirdHystrixTest {
    public static void main(String[] args) throws Exception{
        String result = new MyThirdHystrixCommand("luozy").execute();
        System.out.println("执行结果是：" + result);
        Future<String> future = new MyThirdHystrixCommand("jack").queue();
        System.out.println("执行结果是：" + future.get());
    }
}

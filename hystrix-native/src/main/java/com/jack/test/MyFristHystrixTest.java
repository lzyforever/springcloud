package com.jack.test;

import com.jack.hystrix.MyFirstHystrixCommand;

import java.util.concurrent.Future;

/**
 * 测试一下第一个Hystrix
 */
public class MyFristHystrixTest {
    public static void main(String[] args) throws Exception {
        // 使用execute()方法是同步的方法
        String result = new MyFirstHystrixCommand("luozy").execute();
        // 输出结果：luozy : hystrix-MyGroup-1，构造函数中设置的组名变成了线程的名字
        System.out.println(result);

        // 使用queue()方法是异步的方法
        Future<String> future = new MyFirstHystrixCommand("jack").queue();
        System.out.println(future.get());
    }
}

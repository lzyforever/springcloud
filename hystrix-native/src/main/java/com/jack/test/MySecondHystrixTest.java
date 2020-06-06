package com.jack.test;

import com.jack.hystrix.MySecondHystrixCommand;


/**
 * 测试模拟请求超时的Hystrix
 */
public class MySecondHystrixTest {
    public static void main(String[] args) {
        // 使用execute()方法是同步的方法
        String result = new MySecondHystrixCommand("luozy").execute();
        // 输出结果：失败了啊 T_T !  证明已经触发了回退
        System.out.println(result);
    }
}

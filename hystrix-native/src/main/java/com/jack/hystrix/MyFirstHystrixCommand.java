package com.jack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 构建第一个HystrixCommand对象
 * 首先需要继承HystrixCommand抽象类，通过构造函数设置一个Groupkey
 * 具体的逻辑在run方法中，返回了一个当前线程名称
 *
 */
public class MyFirstHystrixCommand extends HystrixCommand<String> {
    private final String name;
    public MyFirstHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("MyGroup"));
        this.name = name;
    }
    /**
     *  这里面写具体的逻辑
     */
    @Override
    protected String run() throws Exception {
        System.out.println("get data");
        return this.name + " : "+ Thread.currentThread().getName();
    }
}

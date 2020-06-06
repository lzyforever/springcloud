package com.jack.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * 在run方法里面添加超时，模拟请求超时的情况
 */
public class MySecondHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public MySecondHystrixCommand(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("MyGroup"));
        this.name = name;
    }

    /**
     *  这里面写具体的逻辑
     */
    @Override
    protected String run() throws Exception {
        Thread.sleep(1000 * 10);
        System.out.println("get data");
        return this.name + " : "+ Thread.currentThread().getName();
    }

    /**
     * 调用超时失败，会调用这个方法，返回回退内容
     */
    @Override
    protected String getFallback() {
        return "失败了啊 T_T !";
    }
}

package com.jack.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

/**
 * 有缓存的生成，就会有清除缓存，当数据发生变动时，必须将缓存中的数据也更新了
 * 不然就会出现脏数据的问题，同样，Hystrix也提供了清除缓存的功能
 */
public class MySeventhHystrixCommand extends HystrixCommand<String> {

    private final String name;

    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("MyKey");


    public MySeventhHystrixCommand(String name) {
        super(HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("MyGroup")).andCommandKey(GETTER_KEY)
        );
        this.name = name;
    }

    public static void flushCache(String name) {
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(name);
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

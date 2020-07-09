package com.jack.common.redisson.controller;

import com.jack.common.redisson.lock.DistributedLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@RestController
public class TestController {

    private static final String KEY = "redisson_key";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DistributedLocker distributedLocker;

    @GetMapping("/createKey")
    public String createKey() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(KEY, "hello world!");
        return "success";
    }

    @GetMapping("/test")
    public void test() {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                try {
                    System.err.println("开始获取锁，当前线程：" + Thread.currentThread().getName());
                    // 尝试获取锁，等待5秒，自己获得锁后一直不解锁则10秒后自动解锁
                    boolean isGetLock = distributedLocker.tryLock(KEY, TimeUnit.SECONDS, 5L, 10L);
                    if (isGetLock) {
                        System.out.println("线程:" + Thread.currentThread().getName() + ",获取到了锁");
                        // 获得锁之后可以进行相应的处理
                        Thread.sleep(100);
                        System.err.println("已获取锁，进行相应的操作，当前线程：" + Thread.currentThread().getName());
                        distributedLocker.unlock(KEY);
                        System.err.println("释放锁，当前线程：" + Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

}

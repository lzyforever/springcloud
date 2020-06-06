package com.jack.service;

import brave.ScopedSpan;
import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 */
@Service
public class MyServiceImpl implements MyService{

    private Logger logger = LoggerFactory.getLogger(MyServiceImpl.class);

    @Autowired
    private Tracer tracer;

    /**
     * 使用@Async注解，开启异步执行这个方法
     */
    @Async
    @Override
    public void writeMsg(String msg) {
        logger.info("这里是异步线程在执行写入消息：" + msg);
    }

    /**
     * 手动埋点
     */
    @NewSpan(name = "NewSpan")
    @Override
    public void saveLog(String log) {
//        ScopedSpan span = tracer.startScopedSpan("NewSpan");
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
//            span.error(e);
        } finally {
//            span.finish();
        }
    }
}

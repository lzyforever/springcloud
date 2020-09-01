package com.jack.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 事件发布类，用来发布事件
 */
@Component
public class MyEventPublisher implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 注入ApplicationContext
     */
    private ApplicationContext applicationContext;

    /**
     * 使用ApplicationContext的publishEvnet方法来发布
     * @param msg
     */
    public void publish(String msg) {
        applicationContext.publishEvent(new MyEvent(this, msg));
    }

}

package com.jack.configservice.config;

import com.jack.configservice.controller.NotificationControllerV2;
import com.jack.configservice.entity.ReleaseMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 扫描发布消息
 * 消息发送后，根据Apollo里面的Config Service中会启动一个线程定时扫描ReleaseMessage表
 * 查看是否有新的消息记录，然后去通知客端
 */
@Component
public class ReleaseMessageScanner implements InitializingBean {

    @Autowired
    private NotificationControllerV2 notification;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 启动一个新程去扫描，从队列里面取有没有新的配置发布（apollo中是定时任务去数据库扫描）
        new Thread(() -> {
            for(;;) {
                // 循环读取NotificationControllerV2中的队列，如果有消息的话就构造一个ReleaseMessage的对象然后进行处理
                String result = NotificationControllerV2.queue.poll();
                if (result != null) {
                    ReleaseMessage message = new ReleaseMessage();
                    message.setMessage(result);
                    notification.handleMessage(message);
                }
            }
        }).start();
    }
}

package com.jack.configservice.config;

import com.jack.configservice.entity.ReleaseMessage;

/**
 * 发布消息监听接口
 * 当配置发生变化时，发送通知的消息监听器
 */
public interface ReleaseMessageListener {
    /**
     * 处理发布消息
     */
    void handleMessage(ReleaseMessage message);
}

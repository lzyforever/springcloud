package com.jack.kafka.service;

public interface MsgService {
    /**
     * 获取队列消息
     */
    String getMsg();

    /**
     * 获取消息使用的此方法，getMsg反应慢
     */
    String getMsg2();

    /**
     * 修改消息一读壮体啊
     */
    void resolve(Long num);

}

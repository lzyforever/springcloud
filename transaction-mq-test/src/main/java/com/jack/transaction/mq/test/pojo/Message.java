package com.jack.transaction.mq.test.pojo;

/**
 * 消息数据传输对象
 */
public class Message {
    /** 消息ID */
    private Long messageId;

    /** 消息内容 */
    private String message;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

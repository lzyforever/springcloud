package com.jack.transaction.mq.client;

import com.jack.transaction.mq.client.pojo.TransactionMessage;
import com.jack.transaction.mq.client.query.MessageQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * 事务消息回退定义
 */
public class TransactionMqRemoteClientFallback implements TransactionMqRemoteClient {
    @Override
    public boolean sendMessage(TransactionMessage message) {
        return false;
    }

    @Override
    public boolean sendMessage(List<TransactionMessage> messages) {
        return false;
    }

    @Override
    public boolean confirmCustomerMessage(String customerSystem, Long messageId) {
        return false;
    }

    @Override
    public List<TransactionMessage> findByWaitingMessage(int limit) {
        return new ArrayList<>();
    }

    @Override
    public boolean confirmDieMessage(Long messageId) {
        return false;
    }

    @Override
    public boolean incrSendCount(Long messageId, String sendDate) {
        return false;
    }

    @Override
    public boolean retrySendDieMessage() {
        return false;
    }

    @Override
    public List<TransactionMessage> findMessageByPage(MessageQuery query) {
        return new ArrayList<>();
    }
}

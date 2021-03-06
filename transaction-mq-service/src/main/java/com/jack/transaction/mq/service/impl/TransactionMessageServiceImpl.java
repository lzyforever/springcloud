package com.jack.transaction.mq.service.impl;

import com.jack.jdbc.EntityService;
import com.jack.jdbc.PageQueryParam;
import com.jack.transaction.mq.entity.TransactionMessage;
import com.jack.transaction.mq.enums.TransactionMessageStatusEnum;
import com.jack.transaction.mq.service.TransactionMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 消息服务实现类
 */
@Service
public class TransactionMessageServiceImpl extends EntityService<TransactionMessage> implements TransactionMessageService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sendMessage(TransactionMessage message) {
        if (check(message)) {
            super.save(message);
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean sendMessage(List<TransactionMessage> messages) {
        for (TransactionMessage message : messages) {
            if (!check(message)) {
                return false;
            }
        }
        super.batchSave(messages);
        return true;
    }

    private boolean check(TransactionMessage message) {
        if (!StringUtils.hasText(message.getMessage()) || !StringUtils.hasText(message.getQueue()) || !StringUtils.hasText(message.getSendSystem())) {
            return false;
        }
        if (message.getCreateDate() == null) {
            return false;
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean confirmCustomerMessage(String customerSystem, Long messageId) {
        TransactionMessage message = super.getById("id", messageId);
        if (message == null) {
            return false;
        }
        message.setCustomerDate(new Date());
        message.setStatus(TransactionMessageStatusEnum.OVER.getStatus());
        message.setCustomerSystem(customerSystem);
        super.updateByContainsFields(message, "id", TransactionMessage.UPDATE_FIELDS);
        return true;
    }

    @Override
    public List<TransactionMessage> findByWaitingMessage(int limit) {
        if (limit > 1000) {
            limit = 1000;
        }
        return super.listForPage("status", TransactionMessageStatusEnum.WAITING.getStatus(), 0, limit, TransactionMessage.ID_ORDERS);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean confirmDieMessage(Long messageId) {
        TransactionMessage message = super.getById("id", messageId);
        if (message == null) {
            return false;
        }
        message.setStatus(TransactionMessageStatusEnum.DIE.getStatus());
        message.setDieDate(new Date());
        super.updateByContainsFields(message, "id", TransactionMessage.UPDATE_FIELDS2);
        return true;
    }

    @Override
    public boolean incrSendCount(Long messageId, Date sendDate) {
        TransactionMessage message = super.getById("id", messageId);
        if (message == null) {
            return false;
        }
        message.setSendDate(new Date());
        message.setSendCount(message.getSendCount() + 1);
        super.updateByContainsFields(message, "id", new String[]{"send_count", "send_date"});
        return true;
    }

    @Override
    public boolean retrySendDieMessage() {
        super.updateByContainsFields(new String[]{"status", "send_count"}, "status", new Object[]{TransactionMessageStatusEnum.WAITING.getStatus(), 0, TransactionMessageStatusEnum.DIE.getStatus()});
        return true;
    }

    @Override
    public List<TransactionMessage> findMessageByPage(PageQueryParam queryParam, TransactionMessageStatusEnum status) {
        return super.listForPage("status", status.getStatus(), queryParam.getStart(), queryParam.getLimit(), TransactionMessage.ID_ORDERS);
    }
}

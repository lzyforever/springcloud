package com.jack.transaction.mq.service;

import com.jack.jdbc.PageQueryParam;
import com.jack.transaction.mq.entity.TransactionMessage;
import com.jack.transaction.mq.enums.TransactionMessageStatusEnum;

import java.util.Date;
import java.util.List;

/**
 * 消息服务接口
 */
public interface TransactionMessageService {
    /**
     * 发送消息，只存储到消息表中，发送逻辑有具体的发送线程执行
     * @param message 消息内容
     * @return true 成功 | false 失败
     */
    boolean sendMessage(TransactionMessage message);

    /**
     * 批量发送消息，只存储到消息表，发送逻辑有具体的发送线程执行
     * @param messages 消息集合
     * @return true 成功 | false 失败
     */
    boolean sendMessage(List<TransactionMessage> messages);

    /**
     * 确认消息被消费
     * @param customerSystem 消费的系统
     * @param messageId 消息ID
     * @return true 成功 | false 失败
     */
    boolean confirmCustomerMessage(String customerSystem, Long messageId);

    /**
     * 查询最早没有被消费的消息
     * @param limit 查询条数
     * @return 未消费的消息列表
     */
    List<TransactionMessage> findByWaitingMessage(int limit);

    /**
     * 确认消息死亡
     * @param messageId 消息ID
     * @return true 成功 | false 失败
     */
    boolean confirmDieMessage(Long messageId);

    /**
     * 累加发送次数
     * @param messageId 消息ID
     * @param sendDate 发送时间（task服务中的时间，防止服务器之间时间不同步问题）
     * @return true 成功 | false 失败
     */
    boolean incrSendCount(Long messageId, Date sendDate);

    /**
     * 重新发送当前已死亡的消息
     * @return true 成功 | false 失败
     */
    boolean retrySendDieMessage();

    /**
     * 分页查询具体状态的消息
     * @param queryParam 分页参数类
     * @param status 状态
     * @return 消息列表
     */
    List<TransactionMessage> findMessageByPage(PageQueryParam queryParam, TransactionMessageStatusEnum status);
}

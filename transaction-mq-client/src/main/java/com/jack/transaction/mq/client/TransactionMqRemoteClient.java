package com.jack.transaction.mq.client;

import com.jack.transaction.mq.client.pojo.TransactionMessage;
import com.jack.transaction.mq.client.query.MessageQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 事务消息服务调用客户端
 */
@FeignClient(value = "transaction-mq-service", path = "/message", fallback = TransactionMqRemoteClientFallback.class)
public interface TransactionMqRemoteClient {
    /**
     * 发送消息，只存储到消息表中，发送逻辑由具体的发送线程执行
     *
     * @param message 消息内容
     * @return true 成功 | false 失败
     */
    @PostMapping("/send")
    boolean sendMessage(@RequestBody TransactionMessage message);

    /**
     * 批量发送消息，只存储到消息表中，发送逻辑由具体的发送线程执行
     *
     * @param messages 消息内容集合
     * @return true 成功 | false 失败
     */
    @PostMapping("/sends")
    boolean sendMessage(@RequestBody List<TransactionMessage> messages);

    /**
     * 确认消息被消费
     *
     * @param customerSystem 消费的系统
     * @param messageId      消息ID
     * @return true 成功 | false 失败
     */
    @PostMapping("/confirm/customer")
    boolean confirmCustomerMessage(@RequestParam("customerSystem") String customerSystem, @RequestParam("messageId") Long messageId);

    /**
     * 查询最早没有被消费的消息
     *
     * @param limit 查询条数
     * @return 未被消息的消息列表
     */
    @GetMapping("/waiting")
    List<TransactionMessage> findByWaitingMessage(@RequestParam("limit") int limit);

    /**
     * 确认消息死亡
     *
     * @param messageId 消息ID
     * @return true 是 | false 否
     */
    @PostMapping("/confirm/die")
    boolean confirmDieMessage(@RequestParam("messageId") Long messageId);


    /**
     * 累加发送次数
     *
     * @param messageId 消息ID
     * @param sendDate  发送时间（Task服务中的时间，防止服务器之间时间不同步问题）
     * @return true 成功 | false 失败
     */
    @PostMapping("/incrSendCount")
    boolean incrSendCount(@RequestParam("messageId") Long messageId, @RequestParam("sendDate") String sendDate);

    /**
     * 重新发送当前已死亡的消息
     *
     * @return true 成功 | false 失败
     */
    @GetMapping("/send/retry")
    boolean retrySendDieMessage();

    /**
     * 分页查询具体状态的消息
     *
     * @param query 消息查询类
     * @return 消息列表
     */
    @PostMapping("/query")
    List<TransactionMessage> findMessageByPage(@RequestBody MessageQuery query);
}

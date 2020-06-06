package com.jack.transaction.mq.controller;

import com.jack.jdbc.utils.DateUtils;
import com.jack.transaction.mq.entity.TransactionMessage;
import com.jack.transaction.mq.enums.TransactionMessageStatusEnum;
import com.jack.transaction.mq.query.MessageQuery;
import com.jack.transaction.mq.service.TransactionMessageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 提供消息服务接口
 */

@RestController
@RequestMapping(value = "/message")
public class TransactionManageController {

    @Autowired
    private TransactionMessageService transactionMessageService;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    /**
     * 发送消息，只存储到消息表中，发送逻辑由具体的发送线程执行
     *
     * @param message 消息内容
     * @return true 成功 | false 失败
     */
    @PostMapping("/send")
    public boolean sendMessage(@RequestBody TransactionMessage message) {
        return transactionMessageService.sendMessage(message);
    }

    /**
     * 批量发送消息，只存储到消息表中，发送逻辑由具体的发送线程执行
     *
     * @param messages 消息内容集合
     * @return true 成功 | false 失败
     */
    @PostMapping("/sends")
    public boolean sendMessage(@RequestBody List<TransactionMessage> messages) {
        return transactionMessageService.sendMessage(messages);
    }

    /**
     * 确认消息被消费
     * @param customerSystem 消费的系统
     * @param messageId 消息ID
     * @return true 成功 | false 失败
     */
    @PostMapping("/confirm/customer")
    public boolean confirmCustomerMessage(@RequestParam("customerSystem") String customerSystem, @RequestParam("messageId") Long messageId) {
        return transactionMessageService.confirmCustomerMessage(customerSystem, messageId);
    }

    /**
     * 查询最早没有被消费的消息
     * @param limit 查询条数
     * @return 未被消息的消息列表
     */
    @GetMapping("/waiting")
    public List<TransactionMessage> findByWaitingMessage(@RequestParam("limit") int limit) {
        return transactionMessageService.findByWaitingMessage(limit);
    }

    /**
     * 确认消息死亡
     * @param messageId 消息ID
     * @return true 是 | false 否
     */
    @PostMapping("/confirm/die")
    public boolean confirmDieMessage(@RequestParam("messageId") Long messageId) {
        return transactionMessageService.confirmDieMessage(messageId);
    }


    /**
     * 累加发送次数
     * @param messageId 消息ID
     * @param sendDate 发送时间（Task服务中的时间，防止服务器之间时间不同步问题）
     * @return true 成功 | false 失败
     */
    @PostMapping("/incrSendCount")
    public boolean incrSendCount(@RequestParam("messageId") Long messageId, @RequestParam("sendDate") String sendDate) {
        try {
            if (StringUtils.isBlank(sendDate)) {
                return transactionMessageService.incrSendCount(messageId, new Date());
            } else {
                return transactionMessageService.incrSendCount(messageId, DateUtils.str2Date(sendDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 重新发送当前已死亡的消息
     * @return true 成功 | false 失败
     */
    @GetMapping("/send/retry")
    public boolean retrySendDieMessage() {
        return transactionMessageService.retrySendDieMessage();
    }

    /**
     * 分页查询具体状态的消息
     * @param query 消息查询类
     * @return 消息列表
     */
    @PostMapping("/query")
    public List<TransactionMessage> findMessageByPage(@RequestBody MessageQuery query) {
        return transactionMessageService.findMessageByPage(query, TransactionMessageStatusEnum.parse(query.getStatus()));
    }
}


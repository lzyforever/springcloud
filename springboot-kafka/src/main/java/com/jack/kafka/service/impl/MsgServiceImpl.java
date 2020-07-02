package com.jack.kafka.service.impl;

import com.jack.kafka.bean.KafkaOutMsg;
import com.jack.kafka.constant.Status;
import com.jack.kafka.constant.Topic;
import com.jack.kafka.dao.KafkaOutMsgMapper;
import com.jack.kafka.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.*;

@Service
@Slf4j
public class MsgServiceImpl implements MsgService {

    @Autowired
    private KafkaOutMsgMapper kafkaOutMsgMapper;

    /**
     * 初始化消息队列，发送之后暂时保存在list中，然后最早头部读取 earliest
     */
    private static final LinkedList<Long> linkedList = new LinkedList<>();

    @KafkaListener(groupId = "simpleGroup", topics = Topic.SIMPLE)
    public void receive(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer, Acknowledgment ack) {
        log.info("进入到了 MsgServiceImpl 进行消息啦！！！");
        log.info("单独消费者消费消息，topic={}, content={}", topic, record.value());
        log.info("consumer content={}", consumer);
        Optional<Object> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("message data : {}", message);
            KafkaOutMsg msg = new KafkaOutMsg();
            msg.setCreateTime(new Date());
            msg.setNum(System.currentTimeMillis());
            msg.setContent(record.value().toString());
            int num = kafkaOutMsgMapper.insertSelective(msg);
            log.info("保存 outMsg result：{}", num == 1 ? "成功" : "失败");
            linkedList.add(msg.getId());
            // 手动提交消费消息
            ack.acknowledge();
        }
        // 如果需要手工提交异步
        // consumer.commitSync();
        // 如果需要手工提交同步
        // consumer.commitAsync();
    }


    @Override
    public String getMsg() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.4.203:9092,192.168.4.203:9093,192.168.4.203:9094");
        properties.put("group.id", "group1");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        //latest 最新  earliest 最早
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList(Topic.SIMPLE));
        String content = "";
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1000));
        for (ConsumerRecord<String, String> record : records) {
            log.info("获取消息详情，{}", record.value());
            content = record.value();
            if (!StringUtils.isEmpty(content)) {
                KafkaOutMsg msg = new KafkaOutMsg();
                msg.setCreateTime(new Date());
                msg.setNum(System.currentTimeMillis());
                msg.setContent(record.value());
                int num = kafkaOutMsgMapper.insertSelective(msg);
                log.info("保存 outMsg result：{}", num == 1 ? "成功" : "失败");
                return content;
            }
        }
        return content;
    }

    @Override
    public synchronized String getMsg2() {
        log.info("队列消息list，{}", linkedList);
        while (true) {
            if (CollectionUtils.isEmpty(linkedList)) {
                log.info("任务队列为空");
                return null;
            }
            KafkaOutMsg outMsg = kafkaOutMsgMapper.selectByPrimaryKey(linkedList.getFirst());
            if (outMsg == null) {
                log.info("获取档案对象失败");
                //移除队列中数据
                log.info("队列移除的元素={}", linkedList.getFirst());
                linkedList.remove(linkedList.getFirst());
            } else if (Status.NO.equals(outMsg.getStatus())) {
                log.info("队列获取消息，总数={}，本条消息档案号={}", linkedList.size(), outMsg.getNum().toString());
                log.info("队列移除的元素={}", linkedList.getFirst());
                linkedList.remove(linkedList.getFirst());
                return outMsg.getNum().toString();
            } else if (Status.YES.equals(outMsg.getStatus())) {
                log.info("已经消费过");
                log.info("队列移除的元素={}", linkedList.getFirst());
                linkedList.remove(linkedList.getFirst());
            }
        }
    }

    @Override
    public void resolve(Long num) {
        KafkaOutMsg outMsg = new KafkaOutMsg();
        outMsg.setNum(num);
        outMsg.setStatus(new Byte("1"));
        int updateStatus = kafkaOutMsgMapper.updateByNum(outMsg);
        log.info("update outMsg result：{}", updateStatus == 1 ? "成功" : "失败");
    }
}

package com.jack.kafka.consumer;

import com.jack.kafka.bean.KafkaOutMsg;
import com.jack.kafka.constant.Topic;
import com.jack.kafka.dao.KafkaOutMsgMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 消息单个消费者
 */
@Component
@Slf4j
public class MsgSimpleConsumer {

    @Autowired
    private KafkaOutMsgMapper kafkaOutMsgMapper;

    /**
     * id：消费者ID
     * groupId：消费组ID
     * topics：监听的topic，可监听多个
     * topicPartitions：可配置更加详细的监听信息，可指定topic、parition、offset监听。
     *
     */
    @KafkaListener(groupId = "simpleGroup", topics = Topic.SIMPLE, errorHandler = "consumerAwareErrorHandler")
    public void receive(ConsumerRecord<String, Object> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, Consumer consumer, Acknowledgment ack) {
        log.info("进入到了 MsgSimpleConsumer 进行消息啦！！！");
        log.info("单独消费者消费消息，topic={}, content={}", topic, record.value());
        log.info("consumer content={}", consumer);
        Optional<Object> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            log.info("message data : ", message);
            KafkaOutMsg msg = new KafkaOutMsg();
            msg.setCreateTime(new Date());
            msg.setNum(System.currentTimeMillis());
            msg.setContent(record.value().toString());
            int num = kafkaOutMsgMapper.insertSelective(msg);
            log.info("保存 outMsg result：{}", num == 1 ? "成功" : "失败");
            // 手动提交消费消息
            ack.acknowledge();
        }
        // 如果需要手工提交异步
        // consumer.commitSync();
        // 如果需要手工提交同步
        // consumer.commitAsync();
    }


//    /**
//     * 监听topic1的0号分区，同时监听topic2的0号分区和topic2的1号分区里面offset从8开始的消息。
//     */
//    @KafkaListener(id = "consumer1",groupId = "felix-group",topicPartitions = {
//            @TopicPartition(topic = "topic1", partitions = { "0" }),
//            @TopicPartition(topic = "topic2", partitions = "0", partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "8"))
//    })
//    public void onMessage2(ConsumerRecord<?, ?> record) {
//        System.out.println("topic:" + record.topic() + "|partition:" + record.partition() + "|offset:" + record.offset() + "|value:" + record.value());
//    }
//
//    /**
//     * 批量消费
//     *
//     * 接收消息时用List来接收
//     *
//     * 设置application.prpertise开启批量消费即可
//     * #设置批量消费
//     * spring.kafka.listener.type=batch
//     * # 批量消费每次最多消费多少条消息
//     * spring.kafka.consumer.max-poll-records=50
//     *
//     */
//    @KafkaListener(id = "consumer2",groupId = "felix-group", topics = "topic1")
//    public void onMessage3(List<ConsumerRecord<?, ?>> records) {
//        System.out.println(">>>批量消费一次，records.size()=" + records.size());
//        for (ConsumerRecord<?, ?> record : records) {
//            System.out.println(record.value());
//        }
//    }




}

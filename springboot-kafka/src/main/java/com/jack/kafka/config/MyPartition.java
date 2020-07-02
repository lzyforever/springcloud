package com.jack.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 自定义随机分配分区
 *
 * 使用时在配置文件中做以下配置
 * spring.kafka.producer.properties.partitioner.class=com.jack.kafka.config.MyPartition
 *
 * 我们知道，kafka中每个topic被划分为多个分区，那么生产者将消息发送到topic时，具体追加到哪个分区呢？
 * 这就是所谓的分区策略，Kafka 为我们提供了默认的分区策略，同时它也支持自定义分区策略。其路由机制为：
 *
 * 1、若发送消息时指定了分区（即自定义分区策略），则直接将消息append到指定分区；
 *
 * 2、若发送消息时未指定 patition，但指定了 key（kafka允许为每条消息设置一个key），则对key值进行hash计算，根据计算
 * 结果路由到指定分区，这种情况下可以保证同一个 Key 的所有消息都进入到相同的分区；
 *
 * 3、patition 和 key 都未指定，则使用kafka默认的分区策略，轮询选出一个 patition；
 *
 * 我们来自定义一个分区策略，将消息发送到我们指定的partition，首先新建一个分区器类实现Partitioner接口，重写
 * 方法，其中partition方法的返回值就表示将消息发送到几号分区，
 *
 */
@Configuration
@Slf4j
public class MyPartition implements Partitioner {
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        int partitionNum = 0;
        try {
            partitionNum = Integer.parseInt((String) key);
        } catch (Exception e) {
            partitionNum = key.hashCode();
        }
        log.debug("kafkaMessage topic:{}|key:{}|value:{}", topic, key, value);
        return Math.abs(partitionNum % numPartitions);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}

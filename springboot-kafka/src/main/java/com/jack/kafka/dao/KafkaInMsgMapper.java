package com.jack.kafka.dao;

import com.jack.kafka.bean.KafkaInMsg;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaInMsgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KafkaInMsg record);

    int insertSelective(KafkaInMsg record);

    KafkaInMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KafkaInMsg record);

    int updateByPrimaryKey(KafkaInMsg record);

    KafkaInMsg selectByNum(Long num);
}
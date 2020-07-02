package com.jack.kafka.dao;

import com.jack.kafka.bean.KafkaOutMsg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KafkaOutMsgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(KafkaOutMsg record);

    int insertSelective(KafkaOutMsg record);

    KafkaOutMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(KafkaOutMsg record);

    int updateByPrimaryKey(KafkaOutMsg record);

    int updateByNum(@Param("outMsg") KafkaOutMsg outMsg);

}
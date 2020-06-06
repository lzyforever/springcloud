package com.jack.transaction.mq.test.service.impl;

import com.jack.transaction.mq.client.TransactionMqRemoteClient;
import com.jack.transaction.mq.client.pojo.TransactionMessage;
import com.jack.transaction.mq.test.pojo.Student;
import com.jack.transaction.mq.test.service.StudentService;
import com.jack.transaction.mq.test.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 学生服务实现类
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private TransactionMqRemoteClient transactionMqRemoteClient;

    @Transactional
    @Override
    public boolean update(Student student) {
        // 获取student的旧信息
        // 执行修改操作
        // 在本地修改之后，发送消息给其他相关联的服务进行修改操作，保证数据一致性
        TransactionMessage message = new TransactionMessage();
        message.setQueue("jack_queue");
        message.setCreateDate(new Date());
        message.setSendDate(new Date());
        message.setSendSystem("transaction-mq-test");
        message.setMessage(JsonUtils.toJson(new Student(student.getId(), student.getName())));
        // 当消息发送完成后，在消息服务的数据库就会存在一条消息记录
        // 这时消息发送系统就会去获取没有被消费的消息，并将其发送到MQ中
        // 然后我们的消费方就可以进行消息的消费逻辑了
        boolean result = transactionMqRemoteClient.sendMessage(message);
        if (!result) {
            throw new RuntimeException("回滚事务");
        }
        return result;
    }
}

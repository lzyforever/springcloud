package com.jack.mongodb.po;

import com.jack.mongodb.autoid.GeneratedValue;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 学生实体类
 * 定义需要自动增长ID的实体类
 * 注意：自增ID的类型不要定义成Long这种包装类
 * mongoTemplate的源码里面对主键ID的类型有限制
 */
@Data
@Document(collection = "stu")
public class Student {
    /** 主键ID，这里将我们定义的注解加上，注意字段类型不要是包装类 */
    @GeneratedValue
    @Id
    private long id;

    @Field("nm")
    private String name;
}

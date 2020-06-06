package com.jack.mongodb.po;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 人员实体类
 * Spring data Mongodb中创建索引是非常方便的，直接在对应的实体类中用注解标识即可
 * 要给某个字段加索引就在字段上面加上@Indexed注解，里面可以填写对应的参数，在插入数
 * 据的时候，框架会自动根据配置的注解创建对应的索引
 *
 * 比如：
 * 唯一索引：在字段上添加注解@Indexed，参数是unique=true，以后台方式创建索引的参数是background=true
 * 组合索引：需要在类的上面定义@CompoundIndexes注解，参数是@CompoundIndex注解数组，可以传多个，name表
 *           示索引的名称，def表示组合索引的字段和索引存储升序(1)或者降序(-1)
 */
@Data
@Document
@CompoundIndexes({
        @CompoundIndex(name = "city_region_idx", def = "{'city': 1, 'region': 1}")
})
public class Person {

    private String id;

    @Indexed(unique = true)
    private String name;

    @Indexed(background = true)
    private int age;

    private String city;

    private String region;

}

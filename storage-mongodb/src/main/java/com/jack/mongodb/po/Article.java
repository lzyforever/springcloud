package com.jack.mongodb.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * 文章实体类
 * 使用@Document注解标识是一个文档，等同于MySQL中的表，collection值表示Mongodb中集合的名称
 * 不写的话就默认为实体类名article
 *
 * 使用@Id注解标识该字段是一个主键
 *
 * 使用@Field注解标识字段，指定值为字段名称，这里边有个小技巧，之所以Spring Data Mongodb中有
 * 这样的注解，是为了让用户能够自定义字段名称，可以和实体类不一致；还有个好处就是可以用缩写，比
 * 如username我们可以配置成uname或者un，这样会节省存储空间，mongodb的存储方式是key value形式的，每
 * 个key都会重复存储，key其实就占了很大一份存储空间
 */
@Data
@Document(collection = "article")
public class Article {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("url")
    private String url;

    @Field("author")
    private String author;

    @Field("tags")
    private List<String> tags;

    @Field("visit_count")
    private Long visitCount;

    @Field("add_time")
    private Date addTime;
}

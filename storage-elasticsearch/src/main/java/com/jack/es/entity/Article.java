package com.jack.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 定义一个Elasticsearch对应的实体类，用来描述索引的信息
 * 注解@Document可以指定索引的名称indexName及索引的类型type
 * 注解@Field中指定了数据的类型，是否使用分词器，及是否需要存储等信息
 *
 * 需要安装ES，并安装好ik分词插件，注意：版本号，不然有大坑
 *
 */
@Data
@Document(indexName = "jack", type = "article")
public class Article {

    @Id
    @Field(type = FieldType.Integer)
    private Integer id;

    @Field(type = FieldType.Keyword)
    private String sid;

    @Field(type = FieldType.Keyword,  analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Keyword)
    private String content;

}

package com.jack.mongodb.controller;

import com.mongodb.client.ListIndexesIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 人员控制器
 */
@RestController
public class PersonController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/indexList")
    public String indexList() {
        ListIndexesIterable<Document> list = mongoTemplate.getCollection("person").listIndexes();
        for (Document document : list) {
            System.out.println(document.toJson());
        }
        return "success";
    }
}

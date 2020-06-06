package com.jack.mongodb.batchupdate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持批量更新的Mongo操作类
 */
public class MongoBaseDao {

    /**
     * 批量更新
     * ordered 如果为true，一条语句更新失败，剩下的语句将不再执行，如果为false，一条语句更新失败，剩下的将继续执行，默认为true
     */
    public static int batchUpdate(MongoTemplate mongoTemplate, String collName, List<BatchUpdateOptions> options, boolean ordered) {
        DBObject command = new BasicDBObject();
        command.put("update", collName);
        List<BasicDBObject> updateList = new ArrayList<>();
        options.forEach(option -> {
            BasicDBObject update = new BasicDBObject();
            update.put("q", option.getQuery().getQueryObject());
            update.put("u", option.getUpdate().getUpdateObject());
            update.put("upsert", option.isUpsert());
            update.put("multi", option.isMulti());
            updateList.add(update);
        });
        command.put("updates", updateList);
        command.put("ordered", ordered);

        Document commandResult = mongoTemplate.getDb().runCommand((Bson) command);
        return Integer.parseInt(commandResult.get("n").toString());
    }
}

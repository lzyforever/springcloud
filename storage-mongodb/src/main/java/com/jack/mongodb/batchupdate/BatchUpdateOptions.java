package com.jack.mongodb.batchupdate;

import lombok.Data;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 指量更新参数封装类
 */
@Data
public class BatchUpdateOptions {
    private Query query;
    private Update update;
    private boolean upsert = false;
    private boolean multi = false;

    public BatchUpdateOptions() {
        super();
    }

    public BatchUpdateOptions(Query query, Update update) {
        super();
        this.query = query;
        this.update = update;
    }

    public BatchUpdateOptions(Query query, Update update, boolean upsert, boolean multi) {
        super();
        this.query = query;
        this.update = update;
        this.upsert = upsert;
        this.multi = multi;
    }
}

package com.jack.transaction.mq.query;

import com.jack.jdbc.PageQueryParam;

/**
 * 消息查询参数
 */
public class MessageQuery extends PageQueryParam {

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

package com.jack.transaction.mq.client.query;

import com.jack.transaction.mq.client.common.PageQueryParam;

/**
 * 消息查询参数对象
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

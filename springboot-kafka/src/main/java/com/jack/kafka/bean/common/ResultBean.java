package com.jack.kafka.bean.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultBean<T> {
    /**
     * 响应状态
     */
    private int status;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 数据条数
     */
    private Integer total;
    /**
     * 返回数据
     */
    private T data;

}

package com.jack.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.jack.elasticjob.annotation.ElasticJobConf;

import java.util.Arrays;
import java.util.List;

/**
 * 数据流任务 Demo
 */
@ElasticJobConf(name = "MyDataFlowJob")
public class MyDataFlowJob implements DataflowJob<String> {

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        return Arrays.asList("1", "2", "3");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        System.out.println("处理数据：" + list.toString());
    }
}

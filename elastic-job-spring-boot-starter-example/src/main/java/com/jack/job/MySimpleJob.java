package com.jack.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.jack.elasticjob.annotation.ElasticJobConf;


/**
 * 简单任务 Demo
 */
@ElasticJobConf(name = "MySimpleJob", cron = "0/10 * * * * ?", shardingItemParameters = "0=0,1=1", description = "简单任务DEMO")
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        switch (shardingContext.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                System.out.println("do something by sharding item 0");
                break;
            case 1:
                // do something by sharding item 1
                System.out.println("do something by sharding item 1");
                break;
            case 2:
                // do something by sharding item 2
                System.out.println("do something by sharding item 2");
                break;
            // case n: ...
        }
    }
}

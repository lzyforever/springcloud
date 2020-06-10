package com.jack.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.jack.elasticjob.annotation.ElasticJobConf;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基于简单任务实现业务数据分片处理
 */
@ElasticJobConf(name = "MySimpleJob2")
public class MySimpleJob2 implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        // 获取当前节点的分片参数
        String shardParamter = shardingContext.getShardingParameter();
        System.out.println("分片参数：" + shardParamter);
        int value = Integer.parseInt(shardParamter);
        for (int i = 0; i < 1000; i++) {
            // 通过取模的方式跟分片的参数对比，对上了就处理这条数据
            // 同理，你可以使用不同的参数与区分在该片里面你关心的和需要处理的数据
            if (i % 2 == value) {
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                System.out.println("片: " + shardParamter + " 执行时间：" + time + ":开始执行简单任务 ：" + i);
            }
        }
    }
}

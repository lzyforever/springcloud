package com.jack.job;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.jack.job.wx.WxMessageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作业监听器，执行前后发送通过消息
 * 可以使用企业微信或是钉钉、短信、邮件等进行通知消息
 */
public class MessageElasticJobListener implements ElasticJobListener {
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String msg = date + "[elastic-job-test 任务名称: " + shardingContexts.getJobName() + " 任务开始执行]，内容：" + JsonUtils.toJson(shardingContexts);
        // 发送通知消息
        //WxMessageUtil.sendTextMessage(msg);
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String msg = date + "[elastic-job-test 任务名称: " + shardingContexts.getJobName() + " 任务执行完毕]，内容：" + JsonUtils.toJson(shardingContexts);
        // 发送通知消息
        //WxMessageUtil.sendTextMessage(msg);
    }
}

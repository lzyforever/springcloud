package com.jack.scheduler.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 商品秒杀提醒任务
 * 为关注该秒杀商品的用户进行推送提醒
 */
public class GoodSecKillRemindJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(GoodSecKillRemindJob.class);

    /**
     * 定时任务逻辑实现方法，每当触发器触发时会执行该方法逻辑
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 获取任务详情内的数据集合
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        // 获取商品编号
        Long goodId = dataMap.getLong("goodId");

        logger.info("分布式节点 spring-boot-quartz，开始处理秒杀商品：{}，关注用户推送消息.",goodId);

        // 秒杀逻辑
    }
}

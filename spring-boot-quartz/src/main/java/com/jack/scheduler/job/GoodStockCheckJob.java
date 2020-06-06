package com.jack.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 商品库存检查定时任务
 */
public class GoodStockCheckJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(GoodStockCheckJob.class);

    /**
     * 定时任务逻辑实现方法，每当触发器触发时会执行该方法逻辑
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("分布式节点spring-boot-quartz，执行库存检查定时任务，执行时间：{}",new Date());
    }
}

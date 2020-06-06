package com.jack.scheduler.good.service.impl;

import com.jack.scheduler.good.entity.GoodInfo;
import com.jack.scheduler.good.repository.GoodRepository;
import com.jack.scheduler.good.service.GoodService;
import com.jack.scheduler.job.GoodAddJob;
import com.jack.scheduler.job.GoodSecKillRemindJob;
import com.jack.scheduler.job.GoodStockCheckJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * 商品服务实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodServiceImpl implements GoodService {

    /**
     * 注入任务调度器
     */
    @Autowired
    private Scheduler scheduler;

    /**
     * 注入商品数据接口
     */
    @Autowired
    private GoodRepository goodRepository;

    @Override
    public Long saveGood(GoodInfo good) throws Exception {
        // 执行保存操作
        goodRepository.save(good);

        // 构建创建商品定时任务
        buildGoodJob();

        // 构建商品库存定时任务
        buildGoodStockJob();

        // 构建商品描述提醒定时任务
        buildGoodSecKillRemindJob(good.getId());
        

        return good.getId();
    }

    @Override
    public void buildGoodJob() throws Exception {
        // 设置开始时间为1分钟后
        long startAtTime = System.currentTimeMillis() + 1000 * 60;

        // 其中任务的名称以及任务的分组是为了区分任务做的限制
        // 在同一个分组下如果加入同样名称的任务，则会提示任务已经存在，添加失败的提示

        // 任务名称
        String name = UUID.randomUUID().toString();
        // 任务所属分组
        String group = GoodAddJob.class.getName();

        // 通过JobDetail来构建一个任务实例，设置 GoodAddJob 类作为任务运行目标对象
        // 当任务被触发时就会执行 GoodAddJob 内的executeInternal方法

        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(GoodAddJob.class).withIdentity(name, group).build();

        // 一个任务需要设置对应的触发器，触发器也分为很多种
        // 该任务中并没有采用cron表达式来设置触发器，而是调用startAt方法设置任务开始执行时间

        // 创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).startAt(new Date(startAtTime)).build();

        // 最后将任务以及任务的触发器共同交付给任务调度器，这样就完成了一个任务的设置

        // 将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void buildGoodStockJob() throws Exception {
        String name = UUID.randomUUID().toString();
        String group = GoodStockCheckJob.class.getName();

        // 该任务的触发器采用了cron表达式来设置，每隔30秒执行一次任务主体逻辑

        // 创建基于cron表达式的调度器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");

        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(GoodStockCheckJob.class).withIdentity(name, group).build();

        // 任务触发器在创建时cron表达式可以搭配startAt方法来同时使用

        // 创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(scheduleBuilder).build();
        // 将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public void buildGoodSecKillRemindJob(Long goodId) throws Exception {
        // 任务名称
        String name = UUID.randomUUID().toString();
        // 任务所属分组
        String group = GoodSecKillRemindJob.class.getName();

        // 模拟秒杀提醒时间是添加商品后的5分钟，通过调用jobDetail实例的getJobDataMap方法就可以获取该任务
        // 数据集合，直接调用put方法就可以进行设置指定key的值，该集合继承了StringKeyDirtyFlagMap并且实现
        // 了Serializable序列化，因为需要将数据序列化到数据库的qrtz_job_details表内

        // 秒杀开始时间 5分钟后
        long startTime = System.currentTimeMillis() + 1000 * 60 * 5;
        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(GoodSecKillRemindJob.class).withIdentity(name, group).build();
        // 设置任务传递商品编号参数
        jobDetail.getJobDataMap().put("goodId", goodId);
        // 创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).startAt(new Date(startTime)).build();
        // 将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }
}

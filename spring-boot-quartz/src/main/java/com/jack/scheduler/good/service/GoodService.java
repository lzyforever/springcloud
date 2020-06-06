package com.jack.scheduler.good.service;

import com.jack.scheduler.good.entity.GoodInfo;

/**
 * 商品服务接口
 */
public interface GoodService {
    /**
     * 保存商品基本信息
     *
     * @param good 新增商品
     * @return 商品ID
     */
    Long saveGood(GoodInfo good) throws Exception;

    /**
     * 构建创建商品定时任务
     *
     * @throws Exception
     */
    void buildGoodJob() throws Exception;

    /**
     * 构建商品库存定时任务
     *
     * @throws Exception
     */
    void buildGoodStockJob() throws Exception;

    /**
     * 构建商品秒杀提醒定时任务
     *
     * @param goodId 需要通知的用户ID
     * @throws Exception
     */
    void buildGoodSecKillRemindJob(Long goodId) throws Exception;
}

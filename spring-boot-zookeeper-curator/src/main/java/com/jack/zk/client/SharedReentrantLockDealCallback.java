package com.jack.zk.client;

/**
 * 重入共享锁回调接口
 */
public interface SharedReentrantLockDealCallback<T> {
    /**
     * 获取可重入共享锁后的处理方法
     * @return 处理对象
     */
    T deal();
}

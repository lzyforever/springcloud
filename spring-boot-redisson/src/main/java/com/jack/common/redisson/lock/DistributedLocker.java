package com.jack.common.redisson.lock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 */
public interface DistributedLocker {

    /**
     * 获取锁，如果锁不可用，则当前线程处于休眠状态，直到获得锁为止
     *
     * @param lockKey 要锁住的KEY
     * @return 返回锁
     */
    RLock lock(String lockKey);

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     *
     * @param lockKey   要锁住的KEY
     * @param leaseTime 超时时间，单位为秒
     * @return 返回锁
     */
    RLock lock(String lockKey, long leaseTime);

    /**
     * 获取锁,如果锁不可用，则当前线程处于休眠状态，直到获得锁为止。如果获取到锁后，执行结束后解锁或达到超时时间后会自动释放锁
     *
     * @param lockKey 要锁住的KEY
     * @param unit    超时时间单位
     * @param timeout 超时时间，单位为秒
     * @return 返回锁
     */
    RLock lock(String lockKey, TimeUnit unit, long timeout);

    /**
     * 尝试获取锁，获取到立即返回true,未获取到立即返回false
     *
     * @param lockKey 要锁住的KEY
     * @return 是否获取到锁
     */
    boolean tryLock(String lockKey);

    /**
     * 尝试获取锁，在等待时间内获取到锁则返回true,否则返回false,如果获取到锁，则要么执行完后程序释放锁，要么在给定的超时时间leaseTime后释放锁
     *
     * @param lockKey   要锁住的KEY
     * @param unit      时间单位
     * @param waitTime  等待获取锁的时间
     * @param leaseTime 超时时间
     * @return 是否获取到锁
     */
    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    /**
     * 释放锁
     *
     * @param lockKey 要释放的KEY
     */
    void unlock(String lockKey);

    /**
     * 释放锁
     *
     * @param lock 要释放的锁
     */
    void unlock(RLock lock);

    /**
     * 锁是否被任意一个线程锁持有
     *
     * @param lockKey 要锁住的KEY
     * @return 是否锁住
     */
    boolean isLocked(String lockKey);
}

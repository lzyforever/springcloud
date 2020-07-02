package com.jack.zk.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.stereotype.Service;

/**
 * 节点监听器实现
 */
@Service
@Slf4j
public class MyWatcher implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {
        log.info("【Watcher监听事件】={}", watchedEvent.getState());
        log.info("【监听的路径为】={}", watchedEvent.getPath());
        // 三种监听类型：创建，删除，更新
        log.info("【监听的类型为】={}", watchedEvent.getType());
    }
}

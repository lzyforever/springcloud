package com.jack.zk.service.impl;

import com.jack.zk.service.ZkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Zookeeper工具实现类
 */
@Service
@Slf4j
public class ZkServiceImpl implements ZkService {

    @Autowired
    private ZooKeeper zkClient;

    @Override
    public Stat exists(String path, boolean needWatch) {
        try {
            return zkClient.exists(path, needWatch);
        } catch (Exception e) {
            log.error("判断指定节点发生异常，节点路径：{}，异常信息：{}", path, e);
            return null;
        }
    }

    @Override
    public Stat exists(String path, Watcher watcher) {
        try {
            return zkClient.exists(path, watcher);
        } catch (Exception e) {
            log.error("判断指定节点发生异常，节点路径：{}，异常信息：{}", path, e);
            return null;
        }
    }

    @Override
    public boolean createNode(String path, String data) {
        try {
            zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        } catch (Exception e) {
            log.error("创建持久化节点发生异常，节点路径：{}，数据：{}，异常信息：{}", path, data, e);
            return false;
        }
    }

    @Override
    public boolean updateNode(String path, String data) {
        try {
            // zk的数据版本是从0开始计数的，如果客户端传入是-1，则表示zk服务器需要基于最新的数据进行更新，如果对zk的数据节点的更新操作没有原子性要求则可以使用-1
            // version参数指定要更新的数据的版本，如果version和真实的版本不同，更新操作将失败，指定version为-1则忽略版本检查
            zkClient.setData(path, data.getBytes(), -1);
            return true;
        } catch (Exception e) {
            log.error("更新持久化节点发生异常，节点路径：{}，数据：{}，异常信息：{}", path, data, e);
            return false;
        }
    }

    @Override
    public boolean deleteNode(String path) {
        try {
            zkClient.delete(path, -1);
            return true;
        } catch (Exception e) {
            log.error("删除持久化节点发生异常，节点路径：{}，异常信息：{}", path, e);
            return false;
        }
    }

    @Override
    public List<String> getChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren(path, false);
        return children;
    }

    @Override
    public String getData(String path, Watcher watcher) {
        try {
            Stat stat = new Stat();
            byte[] data = zkClient.getData(path, watcher, stat);
            return new String(data);
        } catch (Exception e) {
            log.error("获取持久化节点信息发生异常，节点路径：{}，异常信息：{}", path, e);
            return null;
        }
    }
}

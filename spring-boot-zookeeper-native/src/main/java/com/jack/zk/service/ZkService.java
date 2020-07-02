package com.jack.zk.service;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * Zookeeper工具接口
 */
public interface ZkService {
    /**
     * 判断指定节点是否存在
     * @param path 节点路径
     * @param needWatch  指定是否复用zookeeper中默认的Watch
     * @return 返回该节点对应Stat结构信息
     */
    Stat exists(String path, boolean needWatch);

    /**
     * 检测节点是否存在，并设置监听事件
     * 三种监听类型：创建，删除，更新
     * @param path 节点路径
     * @param watcher 传入指定的监听类
     * @return 返回该节点对应的Stat结构信息
     */
    Stat exists(String path, Watcher watcher);

    /**
     * 创建持久化节点
     * @param path 节点路径
     * @param data 节点对应的数据
     * @return 是否成功
     */
    boolean createNode(String path, String data);

    /**
     * 修改持久化节点
     * @param path 节点路径
     * @param data 修改的数据
     * @return 是否成功
     */
    boolean updateNode(String path, String data);

    /**
     * 删除持久化节点
      * @param path 节点路径
     * @return
     */
    boolean deleteNode(String path);

    /**
     * 获取当前节点的子节点（不包括孙子节点）
     * @param path 节点路径
     * @return 返回当前节点对应的子节点集合
     * @throws KeeperException
     * @throws InterruptedException
     */
    List<String> getChildren(String path) throws KeeperException, InterruptedException;

    /**
     * 获取指定节点的值
     * @param path 节点路径
     * @param watcher 传入指定的监听类
     * @return 节点对应的值
     */
    String getData(String path, Watcher watcher);
}

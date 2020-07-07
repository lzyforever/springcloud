package com.jack.zk.client;

import com.jack.zk.config.ZookeeperProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.CuratorFrameworkFactory.Builder;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper客户端
 */
@Slf4j
@Data
public class ZkClient {

    private CuratorFramework client;

    private ZookeeperProperties zookeeperProperties;

    private PathChildrenCache pathChildrenCache;
    private NodeCache nodeCache;
    private TreeCache treeCache;

    public ZkClient(ZookeeperProperties zookeeperProperties) {
        this.zookeeperProperties = zookeeperProperties;
    }

    /**
     * 初始化Zookeeper客户端
     */
    public void init() {
        try {
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(zookeeperProperties.getBaseSleepTimeMs(), zookeeperProperties.getMaxRetries());

            Builder builder = CuratorFrameworkFactory.builder()
                    .connectString(zookeeperProperties.getServer())
                    .retryPolicy(retryPolicy)
                    .sessionTimeoutMs(zookeeperProperties.getSessionTimeoutMs())
                    .connectionTimeoutMs(zookeeperProperties.getConnectionTimeoutMs())
                    .namespace(zookeeperProperties.getNamespace());

            // 添加权限控制
            if (!StringUtils.isEmpty(zookeeperProperties.getDigest())) {
                builder.authorization("digest", zookeeperProperties.getDigest().getBytes("UTF-8"));
                builder.aclProvider(new ACLProvider() {
                    @Override
                    public List<ACL> getDefaultAcl() {
                        return ZooDefs.Ids.CREATOR_ALL_ACL;
                    }

                    @Override
                    public List<ACL> getAclForPath(String s) {
                        return ZooDefs.Ids.CREATOR_ALL_ACL;
                    }
                });
            }

            client = builder.build();
            client.start();

            // 用来getRandomData模拟服务注册使用
            setTreeCacheListener("/service");

            // 添加连接监听器
            addConnectionStateListener();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置Path Cache, 监控本节点的子节点被创建,更新或者删除，注意是子节点, 子节点下的子节点不能递归监控
     * 事件类型有3个, 可以根据不同的动作触发不同的动作
     * 本例子只是演示, 所以只是打印了状态改变的信息, 并没有在PathChildrenCacheListener中实现复杂的逻辑
     *
     * @param path      监控的节点路径
     * @param cacheData 是否缓存data
     *                  可重复监听
     */
    public void setPathCacheListener(String path, boolean cacheData) {
        try {
            pathChildrenCache = new PathChildrenCache(client, path, cacheData);
            PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) {
                    ChildData data = event.getData();
                    switch (event.getType()) {
                        case CHILD_ADDED:
                            log.info("子节点增加, path={}, data={}", data.getPath(), data.getData());
                            break;
                        case CHILD_UPDATED:
                            log.info("子节点更新, path={}, data={}", data.getPath(), data.getData());
                            break;
                        case CHILD_REMOVED:
                            log.info("子节点删除, path={}, data={}", data.getPath(), data.getData());
                            break;
                        default:
                            break;
                    }
                }
            };
            pathChildrenCache.getListenable().addListener(childrenCacheListener);
            pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            log.error("PathCache监听失败, path={}", path);
        }
    }

    /**
     * 设置Node Cache, 监控本节点的新增,删除,更新
     * 节点的update可以监控到, 如果删除会自动再次创建空节点
     * 本例子只是演示, 所以只是打印了状态改变的信息, 并没有在NodeCacheListener中实现复杂的逻辑
     *
     * @param path             监控的节点路径
     *                         可重复监听
     */
    public void setNodeCacheListener(String path) {
        try {
            nodeCache = new NodeCache(client, path);
            NodeCacheListener nodeCacheListener = new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData childData = nodeCache.getCurrentData();
                    log.info("ZNode节点状态改变, path={}", childData.getPath());
                    log.info("ZNode节点状态改变, data={}", childData.getData());
                    log.info("ZNode节点状态改变, stat={}", childData.getStat());
                }
            };
            nodeCache.getListenable().addListener(nodeCacheListener);
            nodeCache.start();
        } catch (Exception e) {
            log.error("创建NodeCache监听失败, path={}", path);
        }
    }

    /**
     * 设置Tree Cache, 监控本节点的新增,删除,更新
     * 节点的update可以监控到, 如果删除不会自动再次创建
     * 本例子只是演示, 所以只是打印了状态改变的信息, 并没有在NodeCacheListener中实现复杂的逻辑
     *
     * @param path 监控的节点路径
     *             可重复监听
     */
    public void setTreeCacheListener(String path) {
        try {
            treeCache = new TreeCache(client, path);
            //添加错误监听器
            treeCache.getUnhandledErrorListenable().addListener(new UnhandledErrorListener() {
                @Override
                public void unhandledError(String s, Throwable throwable) {
                    log.error("setTreeCacheListener error,  错误原因：{}" ,throwable.getMessage());
                }
            });

            treeCache.getListenable().addListener(new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                    ChildData data = event.getData();
                    if (data != null) {
                        switch (event.getType()) {
                            case NODE_ADDED:
                                log.info("[TreeCache]节点增加, path={}, data={}", data.getPath(), data.getData());
                                break;
                            case NODE_UPDATED:
                                log.info("[TreeCache]节点更新, path={}, data={}", data.getPath(), data.getData());
                                break;
                            case NODE_REMOVED:
                                log.info("[TreeCache]节点删除, path={}, data={}", data.getPath(), data.getData());
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
            treeCache.start();
        } catch (Exception e) {
            log.error("创建TreeCache监听失败, path={}", path);
        }

    }


    /**
     * 添加连接监听器
     */
    private void addConnectionStateListener() {
        client.getConnectionStateListenable().addListener((client, state) -> {
            if (state == ConnectionState.LOST) {
                // 连接丢失
                log.info("lost session with zookeeper");
            } else if (state == ConnectionState.CONNECTED) {
                // 连接新建
                log.info("connected with zookeeper");
            } else if (state == ConnectionState.RECONNECTED) {
                // 重新连接
                log.info("reconnected with zookeeper");
            }
        });
    }

    /**
     * 停止Zookeeper客户端
     */
    public void stop() {
        if (client != null) {
            CloseableUtils.closeQuietly(client);
        }
        if (pathChildrenCache != null) {
            CloseableUtils.closeQuietly(pathChildrenCache);
        }
        if (nodeCache != null) {
            CloseableUtils.closeQuietly(nodeCache);
        }
        if (treeCache != null) {
            CloseableUtils.closeQuietly(treeCache);
        }
    }

    /**
     * 获取客户端
     */
    public CuratorFramework getClient() {
        return client;
    }

    /**
     * 创建节点
     *
     * @param mode     节点类型
     *                 1、PERSISTENT 持久化目录节点，存储的数据不会丢失
     *                 2、PERSISTENT_SEQUENTIAL 顺序自动编号的持久化目录节点，存储的数据不会丢失
     *                 3、EPHEMERAL 临时目录节点，一旦创建这个节点的客户端与服务器端口也就是session超时，这种节点会被自动删除
     *                 4、EPHEMERAL_SEQUENTIAL 临时自动编号节点，一旦创建这个节点的客户端与服务器端口也就是session超时，这种节点
     *                 会被自动删除，并且根据当前已经存在的节点数自动加1，然后返回给客户端已经成功创建的目录节点名
     * @param path     节点名称
     * @param nodeData 节点数据
     */
    public void createNode(CreateMode mode, String path, String nodeData) {
        try {
            // 使用createingParentsIfNeeded()之后Curator能够自动递归创建所有所需的父节点
            client.create().creatingParentsIfNeeded().withMode(mode).forPath(path, nodeData.getBytes("UTF-8"));

        } catch (Exception e) {
            log.error("创建点节失败", e);
        }
    }

    /**
     * 创建节点
     *
     * @param mode 节点类型
     *             1、PERSISTENT 持久化目录节点，存储的数据不会丢失
     *             2、PERSISTENT_SEQUENTIAL 顺序自动编号的持久化目录节点，存储的数据不会丢失
     *             3、EPHEMERAL 临时目录节点，一旦创建这个节点的客户端与服务器端口也就是session超时，这种节点会被自动删除
     *             4、EPHEMERAL_SEQUENTIAL 临时自动编号节点，一旦创建这个节点的客户端与服务器端口也就是session超时，这种节点
     *             会被自动删除，并且根据当前已经存在的节点数自动加1，然后返回给客户端已经成功创建的目录节点名
     * @param path 节点名称
     */
    public void createNode(CreateMode mode, String path) {
        try {
            // 使用createingParentsIfNeeded()之后Curator能够自动递归创建所有所需的父节点
            client.create().creatingParentsIfNeeded().withMode(mode).forPath(path);
        } catch (Exception e) {
            log.error("创建点节失败", e);
        }
    }

    /**
     * 删除节点数据
     *
     * @param path           节点名称
     * @param deleteChildren 是否删除子节点
     */
    public void deleteNode(final String path, boolean deleteChildren) {
        try {
            if (deleteChildren) {
                client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
            } else {
                client.delete().guaranteed().forPath(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点数据，包括子节点
     *
     * @param path 节点名称
     */
    public void deleteNode(final String path) {
        deleteNode(path, true);
    }

    /**
     * 设置指定节点的数据
     *
     * @param path 节点名称
     * @param data 节点数据
     */
    public void setNodeData(String path, byte[] data) {
        try {
            client.setData().forPath(path, data);
        } catch (Exception e) {
            log.error("设置节点数据失败", e);
        }
    }

    /**
     * 获取指定节点的数据
     *
     * @param path 节点名称
     * @return 节点数据
     */
    public byte[] getNodeData(String path) {
        try {
            Byte[] bytes = null;
            if (null != treeCache) {
                ChildData data = treeCache.getCurrentData(path);
                if (null != data) {
                    return data.getData();
                }
            }
            return client.getData().forPath(path);
        } catch (Exception e) {
            log.error("获取指定节点数据失败", e);
        }
        return null;
    }

    /**
     * 获取数据时先同步
     *
     * @param path 节点名称
     * @return
     */
    public byte[] syncNodeData(String path) {
        client.sync();
        return getNodeData(path);
    }

    /**
     * 判断路径是否存在
     *
     * @param path 节点名称
     * @return 是否存在
     */
    public boolean isExistNode(final String path) {
        try {
            client.sync();
            return null != client.checkExists().forPath(path);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取节点的子节点
     *
     * @param path 节点名称
     * @return 节点对应的子节点
     */
    public List<String> getChildren(String path) {
        List<String> childrenList = new ArrayList<>();
        try {
            childrenList = client.getChildren().forPath(path);
        } catch (Exception e) {
            log.error("获取节点{}的子节点出错", path, e);
        }
        return childrenList;
    }

    /**
     * 随机读取一个path子路径，"/"为根节点对应该namespace
     * 先从cache中读取，如果没有，再从zookeeper中查询
     *
     * @param path 节点名称
     * @return 节点对应的数据
     */
    public String getRandomData(String path) {
        try {
            Map<String, ChildData> cacheMap = treeCache.getCurrentChildren(path);
            if (null != cacheMap && cacheMap.size() > 0) {
                log.debug("从节点中获取随机子节点数据, 节点名称：{}", path);
                Collection<ChildData> values = cacheMap.values();
                List<ChildData> list = new ArrayList<>(values);
                Random random = new Random();
                byte[] data = list.get(random.nextInt(list.size())).getData();
                return new String(data, "UTF-8");
            }
            if (isExistNode(path)) {
                log.debug("节点名称：{} 不存在，返回null", path);
                return null;
            } else {
                log.debug("随机读取zookeeper节点数据，节点名称：{}", path);
                List<String> list = client.getChildren().forPath(path);
                if (null == list || list.size() == 0) {
                    log.debug("节点名称：{} 没有对应的子节点，返回null", path);
                    return null;
                }
                Random random = new Random();
                String child = list.get(random.nextInt(list.size()));
                path = path + "/" + child;
                byte[] data = client.getData().forPath(path);
                return new String(data, "UTF-8");
            }
        } catch (Exception e) {
            log.error("获取节点名称：{} 下随机子节点数据失败", path, e);
        }
        return null;
    }

    /**
     * 可重入共享锁 -- Shared Reentrant Lock
     *
     * @param lockPath 节点名称
     * @param time     超时时间
     * @param callback 回调方法
     * @return 返回数据
     */
    public Object getSharedReentrantLock(String lockPath, long time, SharedReentrantLockDealCallback<?> callback) {
        // 类似容器的锁InterProcessMultiLock，它可以把多个锁包含起来像一个锁一样进行操作，简单来说就是对多个锁进行一组操作。
        // 当acquire的时候就获得多个锁资源，否则失败。当release时候释放所有锁资源，不过如果其中一把锁释放失败将会被忽略
        InterProcessLock lock = new InterProcessMutex(client, lockPath);
        try {
            if (!lock.acquire(time, TimeUnit.SECONDS)) {
                log.error("获取锁失败");
                return null;
            }
            log.debug("节点名称：{}，获取锁", lockPath);
            return callback.deal();
        } catch (Exception e) {
            log.error("发生异常", e);
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                log.error("释放锁异常", e);
            }
        }
        return null;
    }

    /**
     * 获取读写锁
     *
     * @param path 节点名称
     * @return 返回读写锁
     */
    public InterProcessReadWriteLock getReadWriteLock(String path) {
        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, path);
        return readWriteLock;
    }

    /**
     * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
     */
    ExecutorService pool = Executors.newFixedThreadPool(2);

    /**
     * 监听数据节点的变化情况
     *
     * @param watchPath 监听的数据节点名称
     * @param listener  监听器
     */
    public void watchPath(String watchPath, TreeCacheListener listener) {
        // NodeCache nodeCache = new NodeCache(client, watchPath, false);
        TreeCache cache = new TreeCache(client, watchPath);
        cache.getListenable().addListener(listener, pool);
        try {
            cache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

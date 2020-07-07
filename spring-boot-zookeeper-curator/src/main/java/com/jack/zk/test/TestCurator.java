package com.jack.zk.test;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCurator {
    private static final String CONNECT_ADDR = "192.168.4.203:3179,192.168.4.203:3180,192.168.4.203:3181";
    private static final int SESSION_TIMEOUT = 5000;

    public static void main(String[] args) throws Exception {
        CuratorFramework client = client();

//         exists(client, "/demo");

//         find(client, "/abc");

//        if (exists(client, "/abc")) {
//            find(client, "/abc");
//            delete(client, "/abc");
//        }

        create(client, "/abc/luozy/jack", "hahah");


        getACL(client, "/abc/luozy/jack");


    }

    /**
     * 创建客户端
     */
    public static CuratorFramework client() throws Exception{
        //重试策略，初试时间1秒，重试10次
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 10);
        //通过工厂创建Curator
        CuratorFramework curator = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(policy)
                // Client端由用户名和密码验证，如user:password，digest的密码生成方式是Sha1摘要的base64形式
                .authorization("digest", "rt:rt".getBytes("UTF-8"))
                // ACL控制，ACL权限用一个int型的十进制数字perms表示
                // perms的5个二进制位分别表示setacl、delete、create、write、read
                // 比如0x1f=adcwr，0x1=----r，0x15=a-c-r。
                .aclProvider(new ACLProvider() {
                    // ZooDefs.Ids.OPEN_ACL_UNSAFE   默认匿名权限，权限scheme id：'world,'anyone，权限位：31（adcwr）
                    // ZooDefs.Ids.READ_ACL_UNSAFE   只读权限，权限scheme id：'world,'anyone，权限位：1（r）
                    @Override
                    public List<ACL> getDefaultAcl() {
                        return ZooDefs.Ids.OPEN_ACL_UNSAFE;
                    }
                    @Override
                    public List<ACL> getAclForPath(String s) {
                        return ZooDefs.Ids.OPEN_ACL_UNSAFE;
                    }
                })
                .build();

        //开启连接
        curator.start();

        return curator;
    }


    /**
     * 判断指定节点是否存在
     */
    public static boolean exists(CuratorFramework curator, String path) throws Exception{
        Stat stat = curator.checkExists().forPath(path);
        if (null != stat) {
            System.out.println("path：" + path + " 存在");
            System.out.println("stat 信息：" + stat);
            return true;
        }
        System.out.println("path：" + path + " 不存在");
        return false;
    }

    /**
     * 创建节点
     */
    public static void create(CuratorFramework curator, String path, String data) throws Exception{
        ExecutorService executor = Executors.newCachedThreadPool();
        // 创建节点，creatingParentsIfNeeded()方法的意思是如果父节点不存在，则在创建节点的同时创建父节点；
        // withMode()方法指定创建的节点类型，跟原生的Zookeeper API一样，不设置默认为PERSISTENT类型。
        curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                //添加回调
                .inBackground((framework, event) -> {
                    System.out.println("Code：" + event.getResultCode());
                    System.out.println("Type：" + event.getType());
                    System.out.println("Path：" + event.getPath());
                }, executor).forPath(path, data.getBytes());
        //为了能够看到回调信息
        Thread.sleep(5000);

    }

    /**
     * 获取节点数据
     */
    public static void find(CuratorFramework curator, String path) throws Exception{
        //获取节点数据
        String data = new String(curator.getData().forPath(path));
        System.out.println("path：" + path + "， data：" + data);
    }

    /**
     * 更新节点数据
     */
    public static void update(CuratorFramework curator, String path, String newData) throws Exception{
        curator.setData().forPath(path, newData.getBytes());
        String data = new String(curator.getData().forPath(path));
        System.out.println(data);
    }

    /**
     * 获取子节点
     */
    public static void child(CuratorFramework curator, String path) throws Exception{
        //获取子节点
        List<String> children = curator.getChildren().forPath(path);
        for (String child : children) {
            System.out.println(child);
        }
    }

    /**
     * 放心的删除节点，deletingChildrenIfNeeded()方法表示如果存在子节点的话，同时删除子节点
     */
    public static void delete(CuratorFramework curator, String path) throws Exception{
        //放心的删除节点，deletingChildrenIfNeeded()方法表示如果存在子节点的话，同时删除子节点
        curator.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
    }

    /**
     * 获取节点对应的权限位
     */
    public static void getACL(CuratorFramework curator, String path) throws Exception{
        Stat stat = new Stat();
        List<ACL> acls = curator.getZookeeperClient().getZooKeeper().getACL(path, stat);
        for (ACL acl : acls) {
            System.out.println("权限scheme id：" + acl.getId());
            // 获取的是十进制的int型数字
            System.out.println("权限位：" + acl.getPerms());
        }
    }


    /**
     * 关闭客户端
     */
    public static void close(CuratorFramework curator) throws Exception{
        curator.close();
    }


}
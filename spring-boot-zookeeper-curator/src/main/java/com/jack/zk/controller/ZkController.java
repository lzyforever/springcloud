package com.jack.zk.controller;

import com.jack.zk.client.ZkClient;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/zk")
@ConditionalOnProperty(value = {"zookeeper.enable"}, matchIfMissing = false)
public class ZkController {

    @Autowired
    private ZkClient zkClient;

    private static final String PREFIX = "/";

    @GetMapping("/create/{path}/{nodeData}")
    public Object create(@PathVariable("path") String path, @PathVariable("nodeData") String nodeData) {
        path = PREFIX + path;
        zkClient.createNode(CreateMode.PERSISTENT, path, nodeData);
        zkClient.setPathCacheListener(path, true);
        //zkClient.setNodeCacheListener(path);
        //zkClient.setTreeCacheListener(path);
        return path;
    }

    @GetMapping("/update/{path}/{nodeData}")
    public Object update(@PathVariable("path") String path, @PathVariable("nodeData") String nodeData) {
        path = PREFIX + path;
        zkClient.setNodeData(path, nodeData.getBytes());
        return path;
    }

    @GetMapping("/delete/{path}")
    public Object delete(@PathVariable("path") String path) {
        path = PREFIX + path;
        zkClient.deleteNode(path);
        return path;
    }

    @GetMapping("/find/{path}")
    public Object find(@PathVariable("path") String path) {
        path = PREFIX + path;
        byte[] nodeData = zkClient.getNodeData(path);
        if (null != nodeData) {
            return new String(nodeData);
        }
        return "无数据...";
    }

    @GetMapping("/lock/{path}/{lockType}")
    public Object getSharedReentrantLock(@PathVariable("path") String path, @PathVariable("lockType") Integer lockType) {

        path = PREFIX + path;

        InterProcessReadWriteLock readWriteLock = zkClient.getReadWriteLock(path);
        InterProcessMutex writeLock = readWriteLock.writeLock();
        InterProcessMutex readLock = readWriteLock.readLock();

        Runnable writeRunnable = () -> {
            try {
                System.out.println("---------write lock------------");
                writeLock.acquire();
                System.out.println("Thread Name：" + Thread.currentThread().getName());
                System.out.println("write acquire");
                Thread.sleep(10_000);
                System.out.println("write release");
                writeLock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable readRunnable = () -> {
            try {
                System.out.println("---------read lock------------");
                readLock.acquire();
                System.out.println("Thread Name：" + Thread.currentThread().getName());
                System.out.println("read acquire");
                Thread.sleep(20_000);
                System.out.println("read release");
                readLock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        if (lockType == 0) {
            new Thread(writeRunnable).start();
        } else if (lockType == 1) {
            new Thread(readRunnable).start();
        }

//        zkClient.getSharedReentrantLock(path, 2, () -> {
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return null;
//        });
        return "success";
    }

    @GetMapping("/watch/{path}")
    public String watchPath(@PathVariable("path") String path) {
        path = PREFIX + path;
        zkClient.watchPath(path, (client, event) -> {
            System.out.println("event：" + event.getType() + " path：" + (null != event.getData() ? event.getData().getPath() : null));

            if (null != event.getData() && null != event.getData()) {
                System.out.println("发生变化的节点内容为：" + new String(event.getData().getData()));
            }
        });
        return "success";
    }


    @GetMapping("/children/{path}")
    public Object getChildren(@PathVariable("path") String path) {
        return zkClient.getChildren(PREFIX + path);
    }

    @GetMapping("/threadName")
    public Object getThreadName() {
        return Thread.currentThread().getName();
    }


    @GetMapping("/random")
    public Object getRandomData() throws KeeperException {
        String path = "/service";
        //服务注册
        zkClient.createNode(CreateMode.fromFlag(1),path + "/hello_service1","http://127.0.0.1:8001/");
        zkClient.createNode(CreateMode.fromFlag(1),path + "/hello_service2","http://127.0.0.1:8002/");
        zkClient.createNode(CreateMode.fromFlag(1),path + "/hello_service3","http://127.0.0.1:8003/");
        return zkClient.getRandomData(path);
    }

}

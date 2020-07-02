package com.jack.zk.controller;

import com.jack.zk.config.MyWatcher;
import com.jack.zk.service.ZkService;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZkController {

    /**
     * 根目录
     */
    private static final String ROOT_PATH = "/";

    @Autowired
    private ZkService zkService;

    @GetMapping("/create")
    public String create(String path, String data) {
        path = ROOT_PATH + path;

        // 查看节点是否存在
        Stat stat = zkService.exists(path, new MyWatcher());

        if (null != stat) {
            return "该节点已存在";
        }

        if (zkService.createNode(path, data)) {
            return "创建成功";
        }

        return "创建失败";
    }

    @GetMapping("/update")
    public String update(String path, String data) {
        path = ROOT_PATH + path;
        // 查看节点是否存在
        Stat stat = zkService.exists(path, new MyWatcher());
        if (null == stat) {
            return "该节点不存在";
        }

        if (zkService.updateNode(path, data)) {
            return "修改成功";
        }

        return "修改失败";
    }

    /**
     * 这里面的Watcher不会生效，因为Watcher只有创建、更新、删除
     * @param path
     * @return
     */
    @GetMapping("/getData")
    public String getData(String path) {
        path = ROOT_PATH + path;
        // 查看节点是否存在
        Stat stat = zkService.exists(path, new MyWatcher());
        if (null == stat) {
            return "该节点不存在";
        }

        return zkService.getData(path, new MyWatcher());
    }

}

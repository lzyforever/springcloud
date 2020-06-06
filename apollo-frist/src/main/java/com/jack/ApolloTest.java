package com.jack;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;

/**
 * 测试一下在Java中使用Apollo
 */
public class ApolloTest {
    public static void main(String[] args) throws InterruptedException {

        // 指定环境的方式：
        // 1、配置文件形式：
        // window（c:\opt\settings\server.properties）、linux或mac（/opt/settings/server.properties）,内容为：env=DEV
        // 2、Java System Properties：
        // java -Denv=DEV -jar xxx.jar
        // 3、在代码中测试的情况下，可以像下面在代码里面指定
        System.setProperty("env", "DEV");
        Config config = ConfigService.getAppConfig();
        String key = "username";
        String defaultValue = "骆治宇";
        String username = config.getProperty(key, defaultValue);
        System.out.println("username=" + username);

        config.addChangeListener(changeEvent -> {
            System.out.println("命名空间：" + changeEvent.getNamespace());
            for (String k : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(k);
                System.out.println(String.format("发现修改   配置key：%s，原来的值：%s, 修改后的值：%s，操作类型是：%s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}

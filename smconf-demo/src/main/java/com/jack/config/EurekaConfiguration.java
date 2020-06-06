package com.jack.config;

import org.cxytiandi.conf.client.annotation.ConfField;
import org.cxytiandi.conf.client.annotation.CxytianDiConf;
import org.cxytiandi.conf.client.core.SmconfUpdateCallBack;
import org.cxytiandi.conf.client.core.rest.Conf;

/**
 * 一、Eureka配置信息
 *
 * 这个类是用来配置管理Eureka的信息，使用注解@CxytianDiConf来标识这是一个Smconf配置类
 * system表示当前是哪个系统在使用
 *
 * evn=true表示当前类下的配置信息通过System.setProperty()将值存储在系统变量中，在代码中可以
 * 通过System.getProperty()来获取，在属性文件中可以通过${key}来获取
 *
 * prefix表示给配置类中的字段加前缀，若字段名为url，prefix为xxx，那么配置的整个key就是xxx.url
 *
 * 二、配置更新回调
 *
 * 在很多时候，当配置修改的时候我们需要做一些操作，比如并发高了，我们就要根据并发量去调整连接池的参数。
 * 用smconf修改配置虽然能够实现推送到各个节点中，但是数据库连接信息已经初始化好了，所以当参数改变的时候
 * 我们还需要重新初始化连接，这个就是需要监控配置更新的事件。
 *
 * 在smconf client中我们可以实现SmconfUpdateCallBack接口来监听修改事件
 *
 */
@CxytianDiConf(system = "jack-common", env = true, prefix = "eureka")
public class EurekaConfiguration implements SmconfUpdateCallBack {

    // http://luozy:jack123@localhost:8761/eureka/,http://luozy:jack123@localhost:8762/eureka/,http://luozy:jack123@localhost:8763/eureka/
    @ConfField("Eureka注册中心地址")
    private String defaultZone = "http://luozy:jack123@localhost:8761/eureka/";

    public String getDefaultZone() {
        return defaultZone;
    }

    public void setDefaultZone(String defaultZone) {
        this.defaultZone = defaultZone;
    }

    /**
     * 配置更新之后，会调用此回调方法
     * 可以在此方法中直接使用this.修改的key，也可以直接使用传入参数Conf对象
     * Conf对象就是本次修改的信息，因为在触发reload方法之前，SpringBean中的值已经被修改了
     */
    @Override
    public void reload(Conf conf) {
        // 执行更新的逻辑
        System.err.println(conf.getKey() + "更新了...");
    }
}

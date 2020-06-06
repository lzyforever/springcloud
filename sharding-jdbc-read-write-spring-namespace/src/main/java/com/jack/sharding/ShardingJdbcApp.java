package com.jack.sharding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Sharding-JDBC读写分离示例
 * 通过sharding.xml的这种是Spring命名空间配置
 *
 * 随着网站业务的不断扩展，数据不断增加，用户越来越多，数据库的压力也就越来越大，有的
 * 业务读取量比写入大，有的业务写入量比读取大。这时候就需要采用读写分离的方式来减轻数据
 * 库的压力，就是一个Master数据库，多个Slave数据库。Master库负责数据更新和实时数据查
 * 询，Slave库负责非实时数据查询。
 *
 * 配置全部放在sharding.xml中，主库和从库的数据源是单独配置的，最后通过<master-slave:data-source>标签
 * 来组装成一个读写分离的数据源
 *
 * 在此demo中，默认是向主库写数据，查询是向从库取数据
 * 在读写分离模式下，最常见的就是刚写完一条数据，然后马上去查却没有查到数据。这是因为写的是主库，查的
 * 是从库，而数据库的主从同步也需要时间，快则几十毫秒，慢则几秒，写完后立即去查，此时数据可能还没被同
 * 步过去。这是一个典型的读写分离产生的问题。
 * Sharding-JDBC提供了基于Hint强制路由主库的功能，通过一行代码就能让我们当前的查询操作强制转发到主库上
 * 面，这样就能解决上面说的刚插入就马上查询所带来的问题。
 *
 * 通过 HintManager.getInstance().setMasterRouteOnly(); 这句代码来实现
 *
 *
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:sharding.xml"})
public class ShardingJdbcApp {
    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApp.class, args);
    }
}

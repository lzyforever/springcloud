# springcloud

### [EurekaDemo](/EurekaDemo)

EurekaDemo为第一个Eureka项目示例，使用SpringBoot搭建第一个Eureka项目

### [EurekaCluster](/EurekaCluster)

EurekaCluster为简单的Eureka集群项目，通过Kibbon提供负载均衡

### [EurekaConfig](/EurekaConfig)

EurekaConfig中配置有以下几点配置

1.Eureka服务器
+ 禁用自己做为一个客户端注册到客服器
+ 禁用抓取服务列表
+ 关闭自我保护机制
+ 设置刷新服务列表时间

2.服务提供者
+ 设置服务提供者自定义信息
+ 设置日志级别
+ 设置心跳时间和实例踢出时间

3.服务调用者
+ 设置服务器服务列表刷新时间
+ 输出服务表列
+ 输出服务提供者配置信息

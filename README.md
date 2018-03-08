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

### [EurekaHealth](/EurekaHealth)

EurekaHealth配置了Eureka客户端健康度，通过实现HealthIndicator和HealthCheckHandler达到客户端状态变更，通知到服务端更状其状态，客户端将获取不到该服务

1.服务提供者
+ 添加SpringBoot提供的actuator实现健康度检测功能
+ 配置服务实例刷新时间
+ 实现HealthIndicator和HealthCheckHandler达到客户端状态变更，通知到服务端更状其状态，客户端将获取不到down掉的服务

### [FirstRibbon](/FirstRibbon)
1.使用原生ribbon的API进行应用

2.通过硬编码的方式进行配置

3.通过配置文件的方式进行配置

### [RibbonRule](/RibbonRule)
1.默认的负载均衡规则的使用

2.内置的负载均衡规则

3.配置负载均衡规则

### [RibbonSpring](/RibbonSpring)
1.自定义规则类
2.自定义配置类
3.通过注解的方式进行配置
4.通过配置文件进行配置
5.调用测试
6.查看SpringCloud封装的Ribbon有哪些默认配置
package com.jack.scheduler.config;

import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;

/**
 * quartz定时任务配置
 */
@Configuration
@EnableScheduling
public class QuartzConfiguration {

    /**
     * 继承SpringBeanJobFactory并实现了ApplicationContextAware接口，实现任务实例化方式
     */
    public static class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

        private transient AutowireCapableBeanFactory beanFactory;

        /**
         * 重写设置ApplicationContext的方法，通过外部实例化时设置ApplicationContext实例对象
         */
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) {
            beanFactory = applicationContext.getAutowireCapableBeanFactory();
        }

        /**
         * 将job实例交给Spring IOC 托管
         * 我们在JOB实例实现类内可以直接使用Spring注入的调用被Spring IOC管理的实例
         * <p>
         * 重写createJobInstance方法，采用AutowireCapableBeanFactory来托管SpringBeanJobFactory类中
         * createJobInstance方法返回的定时任务实例，这样我们就可以在定时任务类内使用Spring IOC相关的
         * 注解进行注入业务逻辑了
         */
        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            Object job = super.createJobInstance(bundle);
            // 将job实例交付给spring ioc
            beanFactory.autowireBean(job);
            return job;
        }
    }

    /**
     * 配置任务工厂实例
     * 任务工厂是配置调度器时所需要的实例，通过jobFactory方法注入ApplicationContext实例来
     * 创建一个 AutowiringSpringBeanJobFactory 对象，并且将对象实例托管到Spring IOC容器内
     *
     * @param applicationContext Spring上下文实例
     * @return
     */
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        // 采用自定义任务工厂 整合Spring实例来完成构建任务
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * 配置任务调度器
     * 使用项目数据源作为quartz数据源
     *
     * 此处使用项目内部数据源的方式设置调度器的jobStore，官方quartz有两种持久化配置方案,可以根据实际的项目需求采取不同的方案
     * 第一种：采用quartz.properties配置文件配置独立的定时任务数据源，可以与使用项目的数据库完全独立
     * 第二种：采用与创建项目同一个数据源，定时任务持久化相关的表与业务逻辑在同一个数据库内
     *
     * 在下面的配置中可以看到schedulerFactoryBean内自动注入了JobFactory实例，也就是我们自定义的AutowiringSpringBeanJobFactory任务工厂实例
     * 另外一个参数就是Datasource，在引入的spring-starter-data-jpa依赖后会根据application.yml文件内数据源
     * 相关配置自动实例化Datasource实例，这里直接注入是没有问题的
     *
     * 通过调用SchedulerFactoryBean对象的setConfigLocation方法来设置quartz定时任务框架的基本配置，配
     * 置文件所在位置resources/quartz.properties => classpath:/quartz.properties
     * 注意：quartz.properties配置文件一定要放在classpath下，放在别的位置有部分功能不会生效。
     *
     * @param jobFactory 自定义配置任务工厂
     * @param dataSource 数据源实例
     * @return
     * @throws Exception
     */
    @Bean(destroyMethod = "destroy", autowire = Autowire.NO)
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, DataSource dataSource) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //将spring管理job自定义工厂交由调度器维护
        schedulerFactoryBean.setJobFactory(jobFactory);
        //设置覆盖已存在的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //项目启动完成后，等待2秒后开始执行调度器初始化
        schedulerFactoryBean.setStartupDelay(2);
        //设置调度器自动运行
        schedulerFactoryBean.setAutoStartup(true);
        //设置数据源，使用与项目统一数据源
        schedulerFactoryBean.setDataSource(dataSource);
        //设置上下文spring bean name
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        //设置配置文件位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        return schedulerFactoryBean;
    }
}

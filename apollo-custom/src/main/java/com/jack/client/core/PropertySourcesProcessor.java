package com.jack.client.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 与Spring集成，获取配置类
 * 为了让客户端项目启动时，能从Apollo config service中拉取配置，需实现EnvironmentAware接口是为了
 * 获取Environment对象，可以在容器实例化bean之前读取bean的信息并修改它
 *
 */
@Component
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private final String APOLLO_PROPERTY_SOURCE_NAME = "ApolloPropertySources";

    private ConfigurableEnvironment environment;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 初始化配置到Spring PropertySource中
        Config config = new Config();
        ConfigPropertySource configPropertySource = new ConfigPropertySource("application", config);

        // 将ConfigPropertySource添加到CompositePropertySource中
        CompositePropertySource composite = new CompositePropertySource(this.APOLLO_PROPERTY_SOURCE_NAME);
        composite.addPropertySource(configPropertySource);

        // 最后将CompositePropertySource添加到 ConfigurableEnvironment中
        environment.getPropertySources().addFirst(composite);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }
}

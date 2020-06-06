package com.jack.client.controller;

import com.jack.client.annotation.SpringValueProcessor;
import com.jack.client.annotation.property.SpringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * 测试加载配置信息的 Controller
 */
@RestController
public class ConfigController {
    @Value("${jackName:luozy}")
    private String jackName;

    @Value("${jackUrl}")
    private String jackUrl;

    @Autowired
    private SpringValueProcessor springValueProcessor;

    @Autowired
    private ConfigurableBeanFactory beanFactory;

    /**
     * 调用此接口，返回的结果是骆治宇http://jack.com
     * 在配置文件中配置的是jackName是luozy，为什么是上面的结果喃，是因为在Config中做了更改
     * 这也证明了在启动的时候可以通过这种方式来覆盖本地的值，这就是Apollo与Spring集成的原理
     */
    @GetMapping("/get")
    public String get() {
        return jackName + jackUrl;
    }

    /**
     * 上面是演示启动时修改本地的配置，那么在运行时如何修改？
     * 原理就是把这些配置都存储起来，当配置发生变化的时候进行修改就可以了
     * 在Apollo中定义了一个 SpringValueProcessor 类，用来处理Spring中值的修改
     * com.jack.client.annotation下的类就是从apollo中拷贝的源码
     * 通过实现 BeanPostProcessor 来处理每个bean中的值，然后将这个配置信息封装成一个SpringValue存储到 SpringValueRegistry 中
     * SpringValueRegistry就是利用Map来存储
     *
     * 当调用本接口时，传入什么值，那么jackName就改成了什么值
     * 可以再次接用上面的/get接口获取修改过后的值，这就是动态修改
     */
    @GetMapping("/update/{value}")
    public String update(@PathVariable String value) {
        Collection<SpringValue> targetValues = springValueProcessor.springValueRegistry.get(beanFactory, "jackName");
        for (SpringValue val : targetValues) {
            try {
                val.update(value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return this.jackName;
    }
}

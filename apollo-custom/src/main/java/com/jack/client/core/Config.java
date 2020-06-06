package com.jack.client.core;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取配置类
 * 在Apollo中Config是一个接口，定义了很多读取配置的方法，通过子类去实现这些方法，这里简化，直接定义一个类，提供两个必要的方法
 * 它就是一个配置类，配置拉取之后会存储在类中，所有配置的读取都必须经过它
 */
public class Config {
    public String getProperty(String key, String defaultValue) {
        if ("jackName".equals(key)) {
            return "骆治宇";
        }
        return null;
    }

    public Set<String> getPropertyNames() {
        Set<String> names = new HashSet<>();
        names.add("jackName");
        return names;
    }
}

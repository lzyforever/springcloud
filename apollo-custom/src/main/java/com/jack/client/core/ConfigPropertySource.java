package com.jack.client.core;

import org.springframework.core.env.EnumerablePropertySource;

import java.util.Set;

/**
 * Config封装类
 * 需要将Config封装成 PropertySource 才能插入到Spring Environment中
 * 它继承于 EnumerablePropertySource，而EnumerablePropertySource继承于PropertySource
 */
public class ConfigPropertySource extends EnumerablePropertySource<Config> {

    private static final String[] EMPTY_ARRAY = new String[0];

    public ConfigPropertySource(String name, Config source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        Set<String> propertyNames = this.source.getPropertyNames();
        if (propertyNames.isEmpty()) {
            return EMPTY_ARRAY;
        }
        return propertyNames.toArray(new String[propertyNames.size()]);
    }

    @Override
    public Object getProperty(String name) {
        return this.source.getProperty(name, null);
    }
}

package com.jack.support;

import java.util.Map;

/**
 * 用于负载各个服务之间的信息的上下文对象
 */
public interface RibbonFilterContext {
    RibbonFilterContext add(String key, String value);

    String get(String key);

    RibbonFilterContext remove(String key);

    Map<String, String> getAttributes();
}

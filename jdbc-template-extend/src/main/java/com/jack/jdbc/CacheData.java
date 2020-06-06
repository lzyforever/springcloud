package com.jack.jdbc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存信息类
 */
public class CacheData {
    /**
     * PO中属性名称与数据库字段名称的缓存映射信息
     * key:com.jack.entity.Student.ld_num
     * value:ldNum
     */
    private static Map<String, String> fieldNameMappingMap = new ConcurrentHashMap<>();

    public static void put(String key, String value) {
        fieldNameMappingMap.put(key, value);
    }

    public static String get(String key) {
        return fieldNameMappingMap.get(key);
    }

}
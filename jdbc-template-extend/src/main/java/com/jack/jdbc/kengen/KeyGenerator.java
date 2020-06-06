package com.jack.jdbc.kengen;

/**
 * 主键生成器
 */
public interface KeyGenerator {
    /**
     * 生成主键
     */
    Number generateKey();
}
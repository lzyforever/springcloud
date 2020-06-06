package com.jack.service;

/**
 * 回调接口
 * 用于执行回调的业务逻辑
 */
public interface Closure<O, I> {
    O execute(I input);
}

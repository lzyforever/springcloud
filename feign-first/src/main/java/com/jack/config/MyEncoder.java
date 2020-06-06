package com.jack.config;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.lang.reflect.Type;

/**
 * 自定义Feign编码器
 */
public class MyEncoder implements Encoder {
    @Override
    public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {
        //这里面是编码的实现逻辑
    }
}

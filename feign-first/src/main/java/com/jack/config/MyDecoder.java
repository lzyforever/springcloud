package com.jack.config;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 自定义Feign解码器
 */
public class MyDecoder implements Decoder {
    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        // 这里面是解码的实现逻辑
        return null;
    }
}

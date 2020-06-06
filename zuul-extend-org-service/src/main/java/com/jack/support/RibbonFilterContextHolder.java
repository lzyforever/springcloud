package com.jack.support;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 负载各个服务之间的信息的上下文对象句柄对象
 * TransmittableThreadLocal 是 Alibaba 对 InheritableThreadLocal 进行封装的线程工具类
 */
public class RibbonFilterContextHolder {
    private static final TransmittableThreadLocal<RibbonFilterContext> contextHolder = new TransmittableThreadLocal<RibbonFilterContext>(){
        @Override
        protected RibbonFilterContext initialValue() {
            return new DefaultRibbonFilterContext();
        }
    };

    public static RibbonFilterContext getCurrentContext() {
        return contextHolder.get();
    }

    public static void clearCurrentContext() {
        contextHolder.remove();
    }

}

package com.jack.support;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 负载各个服务之间的信息的上下文对象句柄对象
 * TransmittableThreadLocal 是 Alibaba 对 InheritableThreadLocal 进行封装的线程工具类
 *
 * 为什么要用InheritableThreadLocal而不是ThreadLocal？在SpringCloud中我们用Hystrix来实现断路器，默认是用信息量
 * 来进行隔离的，信号量的隔离方式用ThreadLocal在线程中传递数据是没问题的，当隔离模式为线程时，Hystrix会将请求
 * 放入Hystrix的线程池中执行，这时候某个请求就由A线程变成B线程了，ThreadLocal必然没有效果了，这时候就用
 * InheritableThreadLocal 来传递数据。
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

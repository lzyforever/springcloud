package com.jack.feign;

import org.springframework.stereotype.Component;

/**
 * 回退的定义，用于UserFeignClient的fallback
 * 需要实现UserFeignClient接口里面的所有方法，返回回退时的内容
 *
 * 缺点：不能记录回退原因，所以建议使用Factory的模式来做fallback
 */
@Component
public class UserFeignClientFallback implements UserFeignClient{
    @Override
    public String hello() {
        return "fail";
    }
}

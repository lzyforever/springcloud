package com.jack.feign;

import com.jack.jwt.common.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 认证服务接口API调用客户端
 */
@FeignClient(value = "jwt-auth-service", path = "/oauth")
public interface AuthRemoteClient {
    /**
     * 调用认证，获取Token
     */
    @PostMapping("/token")
    ResponseData auth(@RequestBody AuthQuery query);
}

package com.jack.auth.controller;

import com.jack.auth.pojo.User;
import com.jack.auth.query.AuthQuery;
import com.jack.auth.service.AuthService;
import com.jack.jwt.common.ResponseData;
import com.jack.jwt.utils.JWTUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证接口控制器
 * 用于调用方进行认证时，认证通过则返回一个加密Token给对方，对方就可以用这个Token去请求别的服务
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * 根据accessKey和secretKey判断用户是否合法
     * @param query API用户认证参数
     */
    @PostMapping("/token")
    public ResponseData auth(@RequestBody AuthQuery query) {
        return process(query);
    }

    @GetMapping("/token")
    public ResponseData oauth(AuthQuery query) {
        return process(query);
    }

    private ResponseData process(AuthQuery query) {
        if (StringUtils.isBlank(query.getAccessKey()) || StringUtils.isBlank(query.getSecretKey())) {
            return ResponseData.failByParam("accessKey and secretKey must be not null");
        }

        User user = authService.auth(query);
        if (user == null) {
            return ResponseData.failByParam("认证失败");
        }

        String token = this.getToken(user.getId().toString());

        return ResponseData.ok(token);
    }

    /**
     * 这里本来应该从Redis的方式进行存和取，为了简化代码就直接放到 WebApplicationContext去存和取
     * @param uid 用户ID
     * @return
     */
    private String getToken(String uid) {
        String token;

        // 先从redis缓存里面取，看有没有该用户对应的token
        Object obj = webApplicationContext.getServletContext().getAttribute("tokenMap");
        Map<String, String> tokenMap = obj == null ? new HashMap<>() : (Map<String, String>) obj;
        if (tokenMap.containsKey(uid)) {
            token = tokenMap.get(uid);
        } else {
            token = JWTUtils.getInstance().getToken(uid);
            tokenMap.put(uid, token);
        }

        // 这里应该存到redis里面，并设置过期时间，过期时间应该比token的失效时间短
        webApplicationContext.getServletContext().setAttribute("tokenMap", tokenMap);
        return token;
    }

}

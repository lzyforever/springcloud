package com.jack.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ************************************
 * Copyright 四川中疗网络科技有限公司 *
 * ************************************
 * Description: TODO
 *
 * @author luozy
 * @version cmnt-cloud V1.0
 * @date 2020/2/17 15:55
 */
@ConfigurationProperties(prefix = "com.jack")
@Component
public class MyConfig {

    private String name;

    private String nickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}

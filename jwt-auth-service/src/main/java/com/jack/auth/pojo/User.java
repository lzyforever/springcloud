package com.jack.auth.pojo;

/**
 * 用户信息
 */
public class User {
    private Long id;

    public User(){}

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

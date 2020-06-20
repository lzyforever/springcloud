package com.jack.encrypt.controller;

import com.jack.encrypt.pojo.User;
import com.jack.encrypt.pojo.UserXml;
import com.jack.encrypt.springboot.annotation.Decrypt;
import com.jack.encrypt.springboot.annotation.Encrypt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * 测试接口
 *
 */
@RestController
public class UserController {

    /**
     *
     * 使用方式一：
     * 可以通过在配置文件中进行此接口是否需要加解密
     * 请求解密：spring.encrypt.requestDecryptUriList[0]=/encryptStr
     * 请求解密参数：spring.encrypt.requestDecryptParams.get$/encryptStr=name
     * 响应加密：spring.encrypt.responseEncryptUriList[0]=/encryptStr
     *
     * 使用方式二：
     * 在此接口上添加注解
     * 请求解密：@Decrypt
     * 请求解密参数：在@Decrypt注解属性中添加decryptParam = "name"
     * 加密：@Encrypt
     */
    @Decrypt(decryptParam = "name")
    @Encrypt
    @GetMapping("/encryptStr")
    public String encryptStr(String name) {
        System.out.println(name);
        return name;
    }

    @Decrypt(decryptParam = "name")
    @Encrypt
    @PostMapping("/encryptStr")
    public String encryptStr2(String name) {
        System.out.println(name);
        return name;
    }

    @Encrypt
    @GetMapping("/encryptEntity")
    public User encryptEntity() {
        User user = new User();
        user.setId(1);
        user.setName("luozy");
        return user;
    }

    @Decrypt
    @Encrypt
    @PostMapping("/save")
    public User save(@RequestBody User user) {
        System.out.println("ID：" +user.getId() + "\t Name：" + user.getName());
        return user;
    }

    @Encrypt
    @RequestMapping(value = "encryptEntityXml", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE})
    public UserXml encryptEntityXml() {
        UserXml user = new UserXml();
        user.setId(1);
        user.setName("luozy");
        return user;
    }

    @Decrypt
    @Encrypt
    @RequestMapping(value = "decryptEntityXml", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_XML_VALUE})
    public String decryptEntityXml(@RequestBody UserXml userXml) {
        System.out.println("id：" + userXml.getId() + " name："  + userXml.getName());
        return userXml.getName();
    }

}

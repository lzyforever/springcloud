package com.jack.base64;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Jdk1.8引入了base64的支持，不需要引入第三方库就可以使用base64
 */
public class Test {
    public static void main(String[] args) {
        String str = "hello world";
        // 进行base64编码
        String encode = Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
        System.out.println(encode);
        
        // 进行base64解码
        String decode = new String(Base64.getDecoder().decode(encode), StandardCharsets.UTF_8);
        System.out.println(decode);
    }
}

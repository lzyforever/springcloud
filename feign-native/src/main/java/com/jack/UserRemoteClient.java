package com.jack;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

/**
 * 调用用户远程接口
 */
public interface UserRemoteClient {
    /**
     * 定义一个GET请求接口
     * 使用原生注解@RequestLine
     */
    @RequestLine("GET /user/hello")
    String hello();


    /**
     * 定义一个POST请求接口
     */
    @RequestLine("POST /user/add/{id}")
    User getUser(@Param("id") String id);

    /**
     *  Path参数可以通过大括号包起来，在参数前面加上@Param进行配对
     *  通过@Headers可以添加请求头信息
     */
    @Headers("Content-Type: application/json")
    @RequestLine("PUT /api/{key}")
    void put(@Param("key") String key, Object value);

    /**
     * 通过@Body添加请求体信息，在参数前面加@Param进行配对
     */
    @Headers("Content-Type: application/json")
    @RequestLine("POST /")
    @Body("%7B\"user_name\": \"{user_name}\", \"password\": \"{password}\"%7D")
    void json(@Param("user_name") String username, @Param("password") String password);

}

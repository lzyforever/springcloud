package com.jack.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 测试客户端
 * 启动测试客户端后，调用服务端的http://localhost:8081/addMsg模拟添加
 * 配置项，此客户端则会收到消息
 * Response code: 200
 * 返回结果：[{"namespaceName":"application","notificationId":1}]
 */
public class ClientTest {
    public static void main(String[] args) {
        reg();
    }

    /**
     * 客户端注册
     */
    private static void reg() {
        System.err.println("客户端注册");
        String result = request("http://localhost:8081/getConfig");
        if (null != result) {
            // 配置有更新，重新拉取配置
            // 处理逻辑
        }
        // 重新注册
        reg();
    }

    private static String request(String url) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL getUrl = new URL(url);
            connection = (HttpURLConnection) getUrl.openConnection();
            connection.setReadTimeout(90000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            int responseCode = connection.getResponseCode();
            System.err.println("Response code: " + responseCode);
            if (200 == responseCode) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuffer result = new StringBuffer();
                String line = null;
                while (null != (line = reader.readLine())) {
                    result.append(line);
                }
                System.err.println("返回结果：" + result);
                return result.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != connection) {
                connection.disconnect();
            }
        }
        return null;
    }
}

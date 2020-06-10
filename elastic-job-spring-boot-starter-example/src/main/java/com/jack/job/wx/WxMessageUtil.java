package com.jack.job.wx;

import com.jack.job.JsonUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 企业微信机器人发送消息
 * https://work.weixin.qq.com/api/doc/90000/90135/90236#%E6%96%87%E6%9C%AC%E6%B6%88%E6%81%AF
 */
public class WxMessageUtil {

    // 填写你自己申请的token，动态获取并缓存起来，过期再去取
    private static String ACCESS_TOKEN = "Vi-35JQvSjrAilhMxStkQz_RV1Ry6bhAGs__ePzFhFsckFw7eLA7nHAcNwiuJcJA6VaERhv8_1sI1ivzqBMQ0V4IAPIjaOl5_tqOlt_gDdNAE1oKsg93qu9QxcfARz7qcML5ChLI_shJakuEvMFAPGmMpmdba9VRggN8IOy844_sp2iEvcgCdVIHCRG-eyCpJrOUPVtpphscSHZwuO-jVg";

    // 企业ID
    private static String CORP_ID = "企业ID";

    // 应用Secret
    private static String CORP_SECRET = "应用Secret";

    // 应用ID
    private static String AGENT_ID = "应用ID";

    // 部门ID，多部门用|分隔
    private static String TO_PARTY = "3|16";

    // 发送API地址
    private static String SEND_API = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + ACCESS_TOKEN;

    // 获取TOKEN地址
    private static String TOKEN_API = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + CORP_ID + "&corpsecret=" + CORP_SECRET;


    /**
     * 发送消息
     */
    public static void sendTextMessage(String msg) {
        try {
            Message message = new Message();
            message.setMsgtype("text");
            message.setAgentid(AGENT_ID);
            message.setToparty(TO_PARTY);
            message.setText(new MessageInfo(msg));
            URL url = new URL(SEND_API);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            String textMessage = JsonUtils.toJson(message);
            byte[] data = textMessage.getBytes();
            out.write(data);
            out.flush();
            out.close();
            System.out.println(conn.getResponseCode());
            InputStream in = conn.getInputStream();
            byte[] data1 = new byte[in.available()];
            in.read(data1);
            System.out.println(new String(data1));
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取TOKEN
     */
    public static String getAccessToken() {
        String rs = "";
        try {
            URL url = new URL(TOKEN_API);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.connect();
            System.out.println(conn.getResponseCode());
            InputStream in = conn.getInputStream();
            byte[] data1 = new byte[in.available()];
            in.read(data1);
            rs = new String(data1);
            System.out.println(rs);
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }


    public static void main(String[] args) {
        WxMessageUtil.sendTextMessage("hello");
        //getAccessToken();
    }
}


package com.jack.jdbc.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Ip地址工具类
 */
public class IpUtils {
    public static String getIpAddress(HttpServletRequest req) {
        String ip = req.getHeader("x-real-ip");
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("x-forwarded-for");
            if (ip != null) {
                ip = ip.split(",")[0].trim();
            }
        }

        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }
}

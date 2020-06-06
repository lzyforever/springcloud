package com.jack.jdbc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

/**
 * 启动参数类
 * 随机端口的使用
 */
public class StartCommand {
    private Logger logger = LoggerFactory.getLogger(StartCommand.class);

    public StartCommand(String[] args) {
        boolean isServerPort = false;
        String serverPort = "";
        if (args != null) {
            for (String arg : args) {
                if (StringUtils.hasText(arg) && arg.startsWith("--server.port")) {
                    isServerPort = true;
                    serverPort = arg;
                    break;
                }
            }
        }

        // 没有指定端口，则随机生成一个可用的端口
        if (!isServerPort) {
            int port = getAvailablePort();
            logger.info("current server.port=" + port);
            System.setProperty("server.port", String.valueOf(port));
        } else {
            logger.info("current server.port=" + serverPort.split("=")[1]);
            System.setProperty("server.port", serverPort.split("=")[1]);
        }
    }


    /**
     * 获取可用端口，范围2000-65535
     * @return
     */
    public static int getAvailablePort() {
        int max = 65535;
        int min = 2000;
        Random random = new Random();
        int port = random.nextInt(max) % (max - min + 1) + min;
        boolean using = isLoclePortUsing(port);
        if (using) {
            return getAvailablePort();
        } else {
            return port;
        }
    }

    /***
     *  返回true代表已占用，返回false代表可以使用
     */
    public static boolean isLoclePortUsing(int port){
        boolean flag = true;
        try {
            flag = isPortUsing("127.0.0.1", port);
        } catch (Exception e) {
        }
        return flag;
    }

    /***
     *  返回true代表已占用，返回false代表可以使用
     */
    public static boolean isPortUsing(String host,int port) {
        boolean flag = false;
        try {
            InetAddress theAddress = InetAddress.getByName(host);
            Socket socket = new Socket(theAddress,port);
            socket.close();
            flag = true;
        } catch (Exception e) {
        }
        return flag;
    }


    public static void main(String[] args) {
        StartCommand sc = new StartCommand(new String[]{"--server.port=123"});
    }
}

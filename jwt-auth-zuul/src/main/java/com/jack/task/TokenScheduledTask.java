package com.jack.task;

import com.jack.feign.AuthQuery;
import com.jack.feign.AuthRemoteClient;
import com.jack.jwt.common.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 定时刷新token任务
 */
@Component
public class TokenScheduledTask {

    private static Logger logger = LoggerFactory.getLogger(TokenScheduledTask.class);

    /** 任务执行周期，根据Token的失效期来设，一定要比失效期短，比如失效期是24小时，这里就是20小时 */
    // public static final long INTERVAL_TIME = 60 * 1000 * 60 * 20;
    public static final long INTERVAL_TIME = 60 * 1000 * 3;

    @Autowired
    private AuthRemoteClient authRemoteClient;

    /**
     * 刷新Token
     */
    @Scheduled(fixedDelay = INTERVAL_TIME)
    public void reloadApiToken() {
        System.out.println("执行reloadApiToken任务开始...");
        String token = this.getToken();
        while (StringUtils.isBlank(token)) {
            try {
                Thread.sleep(1000);
                token = this.getToken();
            } catch (InterruptedException e) {
                logger.error("等待Token刷新超时", e);
                System.err.println("等待Token刷新超时");
                e.printStackTrace();
            }
        }
        System.setProperty("jwt-auth-zuul.auth.token", token);
        logger.info("刷新服务token成功: {}", token);
        System.out.println("刷新服务token成功: "+ token);
        System.out.println("执行reloadApiToken任务结束...");
    }

    /**
     * 注意，这里是服务之间的调用，是知道accesskey和secretKey的，这个从数据库里面去取
     * 这里为了方便就直接写死了
     */
    private String getToken() {
        AuthQuery query = new AuthQuery();
        query.setAccessKey("1");
        query.setSecretKey("1");
        ResponseData response = authRemoteClient.auth(query);
        return response.getData() == null ? "" : response.getData().toString();
    }

}

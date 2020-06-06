package com.jack.job;

import com.dangdang.ddframe.job.executor.handler.JobExceptionHandler;
import com.jack.job.wx.WxMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义异常处理，在任务异常时使用钉钉发送通知
 */
public class CustomJobExceptionHandler implements JobExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(CustomJobExceptionHandler.class);

    @Override
    public void handleException(String jobName, Throwable cause) {
        logger.error(String.format("Job '%s' exception occur in job processing", jobName), cause);
        //WxMessageUtil.sendTextMessage("[elastic-job-test 任务名称：" + jobName + "] 出现异常。" + cause.getMessage());
    }
}

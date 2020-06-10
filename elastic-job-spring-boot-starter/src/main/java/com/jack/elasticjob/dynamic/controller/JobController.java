package com.jack.elasticjob.dynamic.controller;

import com.jack.elasticjob.dynamic.bean.Job;
import com.jack.elasticjob.dynamic.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态任务添加
 * 可用于同一个任务，需要不同的时间来进行触发场景
 */
@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    /**
     * 动态添加任务（适用于脚本逻辑已存在的情况，只是动态添加了触发的时间）
     * @param job 信息信息
     */
    @PostMapping("/job")
    public Object addJob(@RequestBody Job job) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", true);

        if (!StringUtils.hasText(job.getJobName())) {
            result.put("status", false);
            result.put("message", "name not null");
            return result;
        }

        if (!StringUtils.hasText(job.getCron())) {
            result.put("status", false);
            result.put("message", "cron not null");
            return result;
        }

        if (!StringUtils.hasText(job.getJobType())) {
            result.put("status", false);
            result.put("message", "jobType not null");
            return result;
        }

        if ("SCRIPT".equals(job.getJobType())) {
            if (!StringUtils.hasText(job.getScriptCommandLine())) {
                result.put("status", false);
                result.put("message", "scriptCommandLine not null");
                return result;
            }
        } else {
            if (!StringUtils.hasText(job.getJobClass())) {
                result.put("status", false);
                result.put("message", "jobClass not null");
                return result;
            }
        }

        try {
            jobService.addJob(job);
        } catch (Exception e) {
            result.put("status", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    /**
     * 删除动态注册的任务（只删除注册中心中的任务信息）
     * @param jobName 任务名称
     */
    @GetMapping("/job/remove")
    public Object removeJob(String jobName) {
        Map<String, Object> result = new HashMap<>();
        result.put("status", true);
        try {
            jobService.removeJob(jobName);
        } catch (Exception e) {
            result.put("status",false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}

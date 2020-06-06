package com.jack.config;

import com.jack.custom.dingding.DingDingNotifier;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

/**
 * 自定义告警配置类
 */
@Configuration
public class CustomNotifierConfig {
    /**
     * 启用DingDingNotifier
     */
    @Bean
    public DingDingNotifier dingDingNotifier(InstanceRepository repository) {
        return new DingDingNotifier(repository);
    }

    /**
     * 正常情况下，当服务上线下线，网络波动时会发送一个警报，但只会发送一次,这个时候就无法判断服务是不是真正出了问题
     * 正式使用中，我们是需要的是当服务真正挂掉的时候，警报可以发送多条，比如每10秒发送一条，这样
     * 连续性的警报就很容易让维护人员关注和辨别。
     *
     * 通过配置一个RemindingNotifier就可以实现这个功能
     */
    @Primary
    @Bean(initMethod = "start", destroyMethod = "stop")
    public RemindingNotifier remindingNotifier(InstanceRepository repository) {
        RemindingNotifier notifier = new RemindingNotifier(dingDingNotifier(repository), repository);
        // 这里设置的时间间隔是10秒1次，当服务出问题的时候就会每隔10秒发送一次警报，直到服务正常才会停止
        // 这个功能还会引发一个别的问题，如果你的服务是运行在Docker中，采用动态端口的话，当你每次重新发布
        // 服务的时候，端口发生变化，SpringBootAdmin就会认为你的服务一直处于不可用的状态，这点不是特别好解决
        // 这个时候建议将SpringBootAdmin重启一下，这样就能获取最新的服务信息了，规避上面的问题
        notifier.setReminderPeriod(Duration.ofSeconds(10));
        notifier.setCheckReminderInverval(Duration.ofSeconds(10));
        return notifier;
    }
}

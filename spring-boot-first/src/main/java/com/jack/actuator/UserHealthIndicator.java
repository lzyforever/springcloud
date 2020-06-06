package com.jack.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * Description: 继承AbstractHealthIndicator来实现自已的业务逻辑判断健康状态
 *
 * @author luozy
 */
@Component
public class UserHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // 如果是健康的刚builder.up()，如果是不健康的则使用builder.down()，后面的withDetail是
        // 其他的信息
        builder.up().withDetail("status", true);
        //builder.down().withDetail("status", false);
    }
}

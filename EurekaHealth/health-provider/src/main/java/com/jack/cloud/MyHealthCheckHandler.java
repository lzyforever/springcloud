package com.jack.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;

/**
 * 实现健康度检查处理器，同步健度监控状态
 */
@Component
public class MyHealthCheckHandler implements HealthCheckHandler {
	
	@Autowired
	private MyHealthIndicator myHealthIndicator;

	@Override
	public InstanceStatus getStatus(InstanceStatus arg0) {
		Status status = myHealthIndicator.health().getStatus();
		if (status.equals(Status.UP)) {
			return InstanceStatus.UP;
		}
		return InstanceStatus.DOWN;
	}

}

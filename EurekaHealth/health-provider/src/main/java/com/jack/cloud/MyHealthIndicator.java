package com.jack.cloud;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * ÊµÏÖSpringBootµÄ½¡¿µ¼à¿Ø
 */
@Component
public class MyHealthIndicator implements HealthIndicator {

	@Override
	public Health health() {
		
		if (PoliceController.canVisitDB) {
			return new Health.Builder(Status.UP).build();
		}
		return new Health.Builder(Status.DOWN).build();
	}

}

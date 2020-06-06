package com.jack.custom.wx;

import com.jack.custom.JsonUtils;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 服务监控企业微信通知
 * 通过继承AbstractStatusChangeNotifier实现企业微信发送机制
 */
public class WxNotifier extends AbstractStatusChangeNotifier {

    public WxNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
    	String serviceName = instance.getRegistration().getName();
    	String serviceUrl = instance.getRegistration().getServiceUrl();
    	String status = instance.getStatusInfo().getStatus();
    	Map<String, Object> details = instance.getStatusInfo().getDetails();
    	
    	StringBuilder str = new StringBuilder();
    	str.append("【").append(serviceName).append("】");
    	str.append("【服务地址】").append(serviceUrl);
    	str.append("【状态】").append(status);
    	str.append("【详情】").append(JsonUtils.toJson(details));
    	System.err.println(str);
        return Mono.fromRunnable(() -> {
        	WxMessageUtil.sendTextMessage(str.toString());
        });
    }

}
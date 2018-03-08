package com.jack.cloud;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * ͨ��ע��@RibbonClient�ķ�ʽ��������
 * 
 * Ҳ����ͨ�������ļ��ķ�ʽ�������ã��ͻ�������.�����ռ�.������=�Զ������
 * spring-lb-provider.ribbon.NFLoadBalancerRuleClassName=com.jack.cloud.MyRule
 * ����Ҫ����Ĭ�ϵ�default���滻Ϊ��
 * default.ribbon.NFLoadBalancerRuleClassName=com.jack.cloud.MyRule����
 * 
 */
/*@RibbonClient(name="spring-lb-provider", configuration=MyConfig.class)*/
public class MyClient {
	
}

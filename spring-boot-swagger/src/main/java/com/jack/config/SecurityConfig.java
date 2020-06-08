package com.jack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Security的配置类，配置权限控制规则
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 重写此方法进行规则定义
     * /actuator/hystrix.stream不进行权限认证，否则在Hystrix Dashboard中无法显示监控数据
     * 其他的actuator开头的URI和swagger-ui.html都做了权限认证。
     * 当我们访问这些被保护的URI时会先让我们输入账号和密码，认证通过之后就可以访问了
     *
     * 账号密码通过配置文件进行配置
     * spring.security.user.name=jack
     * spring.security.user.password=luozy
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/hystrix.stream").permitAll()
                .antMatchers("/actuator/**", "/swagger-ui.html/**").authenticated()
                .anyRequest().permitAll()
                .and().formLogin().and()
                .httpBasic().and().csrf().disable();
    }
}

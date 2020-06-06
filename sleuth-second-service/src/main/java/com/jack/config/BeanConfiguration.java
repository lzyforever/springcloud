package com.jack.config;

import brave.http.HttpAdapter;
import brave.http.HttpSampler;
import feign.Logger;
import org.springframework.cloud.sleuth.instrument.web.ServerSampler;
import org.springframework.cloud.sleuth.instrument.web.SkipPatternProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class BeanConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean(name = ServerSampler.NAME)
    HttpSampler myHttpSampler(SkipPatternProvider provider) {
        Pattern pattern = provider.skipPattern();
        return new HttpSampler() {
            @Override
            public <Req> Boolean trySample(HttpAdapter<Req, ?> adapter, Req request) {
                String url = adapter.path(request);
                boolean shouldSkip = pattern.matcher(url).matches();
                if (shouldSkip) {
                    return false;
                }
                return null;
            }
        };
    }
}

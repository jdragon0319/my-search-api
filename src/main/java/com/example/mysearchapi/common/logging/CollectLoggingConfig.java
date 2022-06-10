package com.example.mysearchapi.common.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CollectLoggingConfig {

    @Bean
    public CollectLoggingAspect collectLoggingAspect() {
        return new CollectLoggingAspect();
    }
}

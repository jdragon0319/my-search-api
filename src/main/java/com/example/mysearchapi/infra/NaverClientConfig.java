package com.example.mysearchapi.infra;

import com.example.mysearchapi.infra.rest.NaverClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class NaverClientConfig {
    private String naverRestClientCB = "naverRestClientCB";
    private final ObjectMapper mapper;
    private final String url;

    public NaverClientConfig(ObjectMapper objectMapper, @Value("${external.naver.url}") String url) {
        this.mapper = objectMapper;
        this.url = url;
    }

    @Bean
    public NaverClient naverClient() {
        CircuitBreaker circuitBreaker = CircuitBreaker.of(naverRestClientCB, CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slidingWindow(20, 20, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .permittedNumberOfCallsInHalfOpenState(10)
                .waitDurationInOpenState(Duration.ofSeconds(10L))
                .writableStackTraceEnabled(false)
                .build());

        FeignDecorators decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                .build();

        return Resilience4jFeign.builder(decorators)
                .client(new OkHttpClient())
                .encoder(new JacksonEncoder(mapper))
                .decoder(new JacksonDecoder(mapper))
                .options(new Request.Options(3000, TimeUnit.MILLISECONDS, 10000, TimeUnit.MILLISECONDS, true))
                .retryer(new Retryer.Default(100, SECONDS.toMillis(1), 2))
                .logger(new Slf4jLogger(NaverClient.class))
                .logLevel(Logger.Level.FULL)
                .target(NaverClient.class, url);
    }
}

package com.services.thread.config.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorConfig {

    @Bean
    @Primary
    public ExecutorService executorService(){
        int maxThread = 2;
        int filaMax = 100;
        return new ThreadPoolExecutor(
                maxThread,
                maxThread,
                0L, TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(filaMax),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}

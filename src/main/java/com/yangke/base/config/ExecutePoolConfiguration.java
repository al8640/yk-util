package com.yangke.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置
 *
 * @author
 *
 */
@Configuration
public class ExecutePoolConfiguration {

    @Value("${threadpool.corePoolSize}")
    private int corePoolSize;

    @Value("${threadpool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${threadpool.queueCapacity}")
    private int queueCapacity;

    @Value("${threadpool.keepAliveSeconds}")
    private int keepAliveSeconds;

    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds(keepAliveSeconds);
        // 核心线程池数
        pool.setCorePoolSize(corePoolSize);
        // 最大线程
        pool.setMaxPoolSize(maxPoolSize);
        // 队列容量
        pool.setQueueCapacity(queueCapacity);
        pool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy()); // 队列满，线程被拒绝执行策略
        return pool;
    }

}

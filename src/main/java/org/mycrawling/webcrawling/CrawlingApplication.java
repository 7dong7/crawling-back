package org.mycrawling.webcrawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class CrawlingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlingApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); // 기본 스레디의 수 (일단 크롤링 포털 수에 맞게 설정)
        executor.setMaxPoolSize(8); // 최대 스레드 수
        executor.setQueueCapacity(20); // 대기 큐 크기
        executor.setThreadNamePrefix("crawling-"); // 스레드 이름 접두사
        executor.initialize();
        return executor;
    }
}

package com.ibori.framework.observability.config;

import io.micrometer.context.ContextSnapshotFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Map;

@EnableAsync
@AutoConfiguration
public class ObservabilityAsyncConfig {

    /**
     * TaskDecorator
     * 비동기 스레드가 실행되기 직전과 직후에 개입해서,
     * 부모 스레드의 로깅 컨텍스트를 자식 스레드로 복사.
     */
    @Bean
    public TaskDecorator micrometerContextTaskDecorator() {
        var snapshotFactory = ContextSnapshotFactory.builder().build();
        return runnable -> {
            var snapshot = snapshotFactory.captureAll();
            return () -> {
                try( var scope = snapshot.setThreadLocals()) {
                    runnable.run();
                }
            };
        };
    }
}
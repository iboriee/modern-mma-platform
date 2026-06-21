package com.ibori.framework.observability;

import io.micrometer.tracing.Tracer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
public class ObservabilityConfig {

    private final Tracer tracer;

    @PostConstruct
    public void init() {
        log.info("[iBori Framework] Observability (Micrometer Tracing OTLP) Auto-Configured!");
    }

}

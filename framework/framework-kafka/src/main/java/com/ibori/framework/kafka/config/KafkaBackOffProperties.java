package com.ibori.framework.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ibori.framework.kafka.backoff")
public record KafkaBackOffProperties(
        long interval,
        long maxAttempts
) {
    public KafkaBackOffProperties {
        if (interval <= 0) {
            throw new IllegalArgumentException(
                    "ibori.framework.kafka.backoff.interval must be positive, but was: " + interval);
        }
        if (maxAttempts <= 0) {
            throw new IllegalArgumentException(
                    "ibori.framework.kafka.backoff.maxAttempts must be positive, but was: " + maxAttempts);
        }
    }
}
package com.ibori.framework.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "ibori.framework.cache")
public class CacheConfigProperties {

    private Duration defaultTtl = Duration.ofMinutes(10);
    private Map<String, Duration> customTtl = new HashMap<>();

    public void setDefaultTtl(Duration defaultTtl) {
        if (defaultTtl == null || defaultTtl.isNegative() || defaultTtl.isZero()) {
            throw new IllegalArgumentException(
                    "ibori.framework.cache.default-ttl must be positive, but was: " + defaultTtl);
        }
        this.defaultTtl = defaultTtl;
    }
}
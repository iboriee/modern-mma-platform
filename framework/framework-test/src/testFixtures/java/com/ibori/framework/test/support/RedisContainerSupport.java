package com.ibori.framework.test.support;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

public interface RedisContainerSupport {

    @ServiceConnection(name = "redis")
    GenericContainer<?> VALKEY = new GenericContainer<>("valkey/valkey:8-alpine")
            .withExposedPorts(6379)
            .withReuse(true);

    static void startContainer() {
        if (!VALKEY.isRunning()) {
            VALKEY.start();
        }
    }
}
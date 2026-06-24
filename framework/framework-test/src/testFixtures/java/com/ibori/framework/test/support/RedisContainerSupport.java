package com.ibori.framework.test.support;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.GenericContainer;

public interface RedisContainerSupport {

    String IMAGE = ImageResolver.get("image.valkey", "valkey/valkey:8-alpine");

    @ServiceConnection(name = "redis")
    GenericContainer<?> VALKEY = new GenericContainer<>(IMAGE)
            .withExposedPorts(6379)
            .withReuse(true);

    static void startContainer() {
        if (!VALKEY.isRunning()) {
            VALKEY.start();
        }
    }
}
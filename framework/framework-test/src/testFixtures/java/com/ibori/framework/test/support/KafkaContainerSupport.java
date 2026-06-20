package com.ibori.framework.test.support;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

//@Testcontainers
public interface KafkaContainerSupport {

    //@Container
    @ServiceConnection
    KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.7.0"))
            .withReuse(true);

    static void startContainer() {
        if (!KAFKA.isRunning()) {
            KAFKA.start();
        }
    }
}
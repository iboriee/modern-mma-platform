package com.ibori.framework.test.support;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public interface KafkaContainerSupport {

    String IMAGE = ImageResolver.get("image.kafka", "confluentinc/cp-kafka:7.7.0");

    @ServiceConnection
    KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse(IMAGE))
            .withReuse(true);

    static void startContainer() {
        if (!KAFKA.isRunning()) {
            KAFKA.start();
        }
    }
}
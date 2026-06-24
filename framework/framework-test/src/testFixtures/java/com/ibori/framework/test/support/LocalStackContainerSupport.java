package com.ibori.framework.test.support;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

public interface LocalStackContainerSupport {

    @ServiceConnection
    LocalStackContainer LOCAL_STACK = new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.4.0"))
            .withServices(LocalStackContainer.Service.S3, LocalStackContainer.Service.SQS)
            .withReuse(true);

    static void startContainer() {
        if (!LOCAL_STACK.isRunning()) {
            LOCAL_STACK.start();
        }
    }
}

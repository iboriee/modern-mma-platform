package com.ibori.framework.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * 통합 테스트 부모 클래스
 * 모든 도메인의 통합 테스트는 이 클래스를 상속받아 작성한다.
 */
@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

    // PostgreSQL 17 컨테이너 (실제 운영 환경과 동일한 버전으로)
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

    // Valkey 8 컨테이너 (Redis 100% 호환 대체제)
    @ServiceConnection(name = "redis")
    static GenericContainer<?> valkey = new GenericContainer<>("valkey/valkey:8-alpine")
            .withExposedPorts(6379);

    static {
        postgres.start();
        valkey.start();
    }
}
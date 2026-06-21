import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("java-convention")
    id("java-library")          // 'api' 사용하기 위한 라이브러리 플러그인
}

// Spring Boot 플러그인이 적용되어 있을 때는 bootJar 생성하지 않음
pluginManager.withPlugin("org.springframework.boot") {
    tasks.withType<BootJar>().configureEach {
        enabled = false
    }
}

tasks.jar {
    enabled = true
}

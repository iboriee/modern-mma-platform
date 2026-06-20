import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("java-convention")       // java 기본 설정 상속
    id("java-library")          // 'api' 키워드를 사용하기 위한 라이브러리 플러그인
}

// Spring Boot 플러그인이 적용되어 있을 때는 bootJar 생성하지 않음
pluginManager.withPlugin("org.springframework.boot") {
    tasks.withType<BootJar>().configureEach {
        enabled = false
    }
}

// 다른 모듈에서 의존성으로 가져다 쓸 수 있도록 일반 Jar 생성 활성화
tasks.jar {
    enabled = true
}

rootProject.name = "modern-mma-platform"

// 빌드 로직(컨벤션 플러그인) 포함
pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

// 프레임워크 모듈
include(":framework:framework-core")
include(":framework:framework-response")
include(":framework:framework-exception")
include(":framework:framework-web")
include(":framework:framework-jpa")
include(":framework:framework-logging")
include(":framework:framework-observability")
include(":framework:framework-kafka")
include(":framework:framework-cache")
include(":framework:framework-security")
// todo: framework lib 추가

// 샘플 도메인 모듈 (모듈러 모놀리스 예시)
include(":modules:sample-order")

// 단일 실행 진입점
include(":bootstrap")
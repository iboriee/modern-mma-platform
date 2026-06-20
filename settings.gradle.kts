rootProject.name = "modern-msa-platform"

// 빌드 로직(컨벤션 플러그인) 포함
pluginManagement {
    includeBuild("build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
// 프레임워크 모듈들 포함
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
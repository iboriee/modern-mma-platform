plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {
    // 🌟 스프링 부트 테스트 코어
    api(libs.spring.boot.starter.test)
    api(libs.spring.boot.testcontainers)

    api(libs.junit.jupiter)

    api(libs.test.postgresql)
    api(libs.test.kafka)
}
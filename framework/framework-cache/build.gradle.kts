plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {

    api(libs.spring.boot.starter.cache)
    api(libs.spring.boot.starter.redis)
    api(libs.spring.boot.configuration.processor)
}
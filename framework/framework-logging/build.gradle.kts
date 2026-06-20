plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {

    api(libs.logstash.logback.encoder)

    compileOnly(libs.spring.boot.starter.web)
}
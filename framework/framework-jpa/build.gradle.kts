plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {
    api(libs.spring.boot.starter.data.jpa)
}
plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {

    api(project(":framework:framework-response"))
    api(project(":framework:framework-exception"))

    api(libs.spring.boot.starter.web)
}
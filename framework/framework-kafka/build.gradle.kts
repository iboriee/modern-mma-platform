plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {

    implementation(project(":framework:framework-core"))
    api(libs.spring.boot.starter.kafka)
}
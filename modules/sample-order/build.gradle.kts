plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {
    api(project(":framework:framework-core"))
    api(project(":framework:framework-response"))
    api(project(":framework:framework-exception"))
    api(project(":framework:framework-web"))
    api(project(":framework:framework-jpa"))

    runtimeOnly("com.h2database:h2")
}
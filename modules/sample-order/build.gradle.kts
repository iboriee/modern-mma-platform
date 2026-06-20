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
    api(project(":framework:framework-kafka"))
    api(project(":framework:framework-cache"))

    testImplementation(testFixtures(project(":framework:framework-test")))
    testRuntimeOnly("com.h2database:h2")
}
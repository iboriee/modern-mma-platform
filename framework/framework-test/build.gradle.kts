plugins {
    id("library-convention")
    id("spring-boot-convention")
    id("java-test-fixtures")
}

dependencies {

    //testFixturesApi 용 Spring Boot BOM 명시
    testFixturesApi(platform("org.springframework.boot:spring-boot-dependencies:${libs.versions.springBoot.get()}"))

    testFixturesApi(libs.spring.boot.starter.test)
    testFixturesApi(libs.spring.boot.testcontainers)

    testFixturesApi(libs.spring.kafka.test)

    testFixturesApi(libs.junit.jupiter)

    testFixturesApi(libs.test.postgresql)
    testFixturesApi(libs.test.kafka)

    testFixturesApi(libs.postgresql.driver)
}
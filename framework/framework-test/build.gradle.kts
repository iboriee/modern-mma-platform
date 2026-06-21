plugins {
    id("library-convention")
    id("spring-boot-convention")
    id("java-test-fixtures")
}

dependencies {

    testFixturesApi(libs.spring.boot.starter.test)
    testFixturesApi(libs.spring.boot.testcontainers)

    testFixturesApi(libs.spring.kafka.test)
    testFixturesApi(libs.test.kafka)

    testFixturesApi(libs.junit.jupiter)

    testFixturesApi(libs.test.postgresql)
    testFixturesApi(libs.postgresql.driver)

}
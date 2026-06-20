plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {

    api("org.springframework:spring-web")
    api(libs.spring.boot.starter.validation)

}
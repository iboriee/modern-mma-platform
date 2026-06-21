plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {

    implementation("org.springframework:spring-web")
    implementation(libs.spring.boot.starter.validation)

}
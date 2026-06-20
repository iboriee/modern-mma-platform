plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {

    implementation(project(":framework:framework-exception"))
    compileOnly(project(":framework:framework-cache"))
    testImplementation(project(":framework:framework-cache"))

    implementation(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.security)

    api(libs.jjwt.api)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.jackson)


}
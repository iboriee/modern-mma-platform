plugins {
    `kotlin-dsl`
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    // Spring Boot 플러그인을 컨벤션 플러그인에서 사용할 수 있도록 주입
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${libs.findVersion("springBoot").get()}")

}
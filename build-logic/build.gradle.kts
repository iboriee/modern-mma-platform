import java.io.File

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

// settings에서 불러온 libs 버전 카탈로그 객체
val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation("org.springframework.boot:spring-boot-gradle-plugin:${libs.findVersion("springBoot").get()}")
}

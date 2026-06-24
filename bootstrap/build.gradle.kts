plugins {
    id("spring-boot-convention")
}

dependencies {
    implementation(project(":modules:sample-order"))

    runtimeOnly("com.h2database:h2")
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
}
plugins {
    id("spring-boot-convention")
}

dependencies {
    implementation(project(":modules:sample-order"))
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
}
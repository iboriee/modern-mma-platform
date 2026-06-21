plugins {
    id("spring-boot-convention")
}

dependencies {
    implementation(platform("org.springframework.modulith:spring-modulith-bom:1.3.0"))
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
}

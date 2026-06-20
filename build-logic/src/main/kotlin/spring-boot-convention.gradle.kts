plugins {
    id("java-convention") // java-convention 규칙 상속
    id("org.springframework.boot")

}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.5.0"))

    implementation("org.springframework.boot:spring-boot-starter")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
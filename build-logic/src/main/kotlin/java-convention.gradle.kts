plugins {
    java
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    // Java 21 컴파일러 옵션 (파라미터 이름 유지 등)
    options.compilerArgs.addAll(listOf("-parameters"))
}

repositories {
    mavenCentral()
}

dependencies {
    val springBootBom = platform("org.springframework.boot:spring-boot-dependencies:3.5.0")
    implementation(springBootBom)
    annotationProcessor(springBootBom)
    testImplementation(springBootBom)
    testAnnotationProcessor(springBootBom)

    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
plugins {
    java
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    val springbootVersion = "${libs.findVersion("springBoot").get()}"
    val springBootBom = platform("org.springframework.boot:spring-boot-dependencies:$springbootVersion")

    implementation(springBootBom)
    annotationProcessor(springBootBom)
    testImplementation(springBootBom)
    testAnnotationProcessor(springBootBom)

    pluginManager.withPlugin("java-test-fixtures") {
        add("testFixturesApi", springBootBom)
        add("testFixturesImplementation", springBootBom)
    }
}
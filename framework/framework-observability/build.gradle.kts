plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {
    api(project(":framework:framework-logging"))
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("io.micrometer:micrometer-tracing-bridge-otel")
    api("io.opentelemetry:opentelemetry-exporter-otlp")
}
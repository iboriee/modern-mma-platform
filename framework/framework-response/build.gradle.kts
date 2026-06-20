plugins {
    id("library-convention")
    id("spring-boot-convention")
}

dependencies {
    // JSON 직렬화/역직렬화를 위한 Jackson (공통 필수)
    api(libs.jackson.databind)
    // @JsonInclude 등의 어노테이션 사용
    api(libs.jackson.annotations)
}
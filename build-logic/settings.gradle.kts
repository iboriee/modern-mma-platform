rootProject.name = "build-logic"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // 루트 프로젝트의 toml 파일 경로 매핑
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
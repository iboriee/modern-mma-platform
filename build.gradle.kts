import org.gradle.api.artifacts.ProjectDependency
import java.io.File

plugins {
    // todo: SonarQube
}


tasks.register<Delete>("clean") {
    delete(layout.buildDirectory)
}

//검증용!!!
tasks.register("printAllModulesDependencies") {
    doLast {
        // 1. gradle/libs.versions.toml 파일 직접 읽어서 매핑 테이블 만들기
        val tomlFile = rootDir.resolve("gradle/libs.versions.toml")
        val libraryMapping = mutableMapOf<String, String>() // "group:name" -> "libs.alias"

        if (tomlFile.exists()) {
            var currentSection = ""
            tomlFile.forEachLine { line ->
                val trimmed = line.trim()
                if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
                    currentSection = trimmed.lowercase()
                } else if (currentSection == "[libraries]" && trimmed.contains("=")) {
                    // 예시: jackson-databind = { module = "com.fasterxml.jackson.core:jackson-databind", ... }
                    val parts = trimmed.split("=", limit = 2)
                    val alias = parts[0].trim()
                    val value = parts[1].trim()

                    // module 또는 group/name 추출을 위한 정규식
                    val moduleMatch = "module\\s*=\\s*\"([^\"]+)\"".toRegex().find(value)
                    val groupMatch = "group\\s*=\\s*\"([^\"]+)\"".toRegex().find(value)
                    val nameMatch = "name\\s*=\\s*\"([^\"]+)\"".toRegex().find(value)

                    val key = when {
                        moduleMatch != null -> moduleMatch.groupValues[1]
                        groupMatch != null && nameMatch != null -> "${groupMatch.groupValues[1]}:${nameMatch.groupValues[1]}"
                        else -> null
                    }

                    if (key != null) {
                        // TOML의 하이픈(-)은 코틀린에서 점(.)으로 접근하므로 변환 (jackson-databind -> libs.jackson.databind)
                        val cleanAlias = alias.replace("-", ".")
                        libraryMapping[key] = "libs.$cleanAlias"
                    }
                }
            }
        }

        // 2. 모든 서브프로젝트 순회하며 직접 선언한 의존성 출력
        subprojects {
            val sub = this
            println("\n=========================================")
            println("📦 Module: :${sub.name}")
            println("=========================================")

            sub.configurations.configureEach {
                val config = this
                if (config.dependencies.isEmpty()) return@configureEach

                println("  [ Configuration: ${config.name} ]")
                config.dependencies.forEach { dep ->
                    if (dep is ProjectDependency) {
                        // 안전하게 이름만 따서 project 경로 매핑
                        println("    - project(':${dep.name}')")
                    } else {
                        val key = "${dep.group}:${dep.name}"
                        val aliasName = libraryMapping[key]
                        val version = dep.version ?: "managed"

                        if (aliasName != null) {
                            println("    - $aliasName -> $key:$version")
                        } else {
                            println("    - $key:$version")
                        }
                    }
                }
            }
        }
    }
}
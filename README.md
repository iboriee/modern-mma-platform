# Modern MSA Platform Framework

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-brightgreen?style=flat-square&logo=springboot)
![Gradle](https://img.shields.io/badge/Gradle-9.x-02303A?style=flat-square&logo=gradle)
![MSA](https://img.shields.io/badge/Architecture-MSA-blue?style=flat-square)

> 최신 생태계(Java 21, Spring Boot 3.5)를 기반으로 구축한 **사내 표준 MSA 프레임워크 및 플랫폼**입니다.
> 각 마이크로서비스가 공통으로 가져야 할 횡단 관심사(통신, 관측 가능성, 예외 처리 등)를 중앙 통제하고 개발 생산성을 극대화하는 것을 목표로 합니다.

## Project Structure (멀티 모듈 아키텍처)

본 프로젝트는 불필요한 의존성 전파를 막고 빌드 속도를 최적화하기 위해 **Gradle Convention Plugins (`build-logic`)** 기반의 멀티 모듈로 구성되어 있습니다.

```text
modern-msa-platform/
 ├── 📁 build-logic/                 # 빌드 컨벤션 플러그인 (의존성/버전 중앙 제어)
 │    ├── java-convention            # Java 21 공통 설정
 │    ├── spring-boot-convention     # Spring Boot 3.5 기본 설정
 │    └── library-convention         # 프레임워크 라이브러리용 (BootJar 비활성화 등)
 │
 ├── 📁 framework/                   # MSA 공통 프레임워크 모듈 (Libraries)
 │    ├── framework-core/            # 글로벌 유틸 및 공통 포맷
 │    ├── framework-response/        # API 공통 응답 규격
 │    ├── framework-exception/       # Global Exception Handler
 │    ├── framework-web/             # Web MVC, Filter, Http Interface 기반 설정
 │    ├── framework-jpa/             # JPA Auditing, DB 설정
 │    └── ... (observability, kafka, test 등 확장 예정)
 │
 ├── 📁 services/                    # 실제 마이크로서비스 (예정)
 │    ├── member-service/            # 회원 도메인(예시)
 │    └── order-service/             # 주문 도메인(예시)
 │
 └── 📄 build.gradle.kts             # Root Build Script (TODO: SonarQube 등 전역 설정)
```

## Core Technology Stack

- **Language:** Java 21 (Virtual Threads 적극 활용)
- **Framework:** Spring Boot 3.5.x
- **Build Tool:** Gradle 9.x (Kotlin DSL)
- **Database:** PostgreSQL 17, Valkey 8 (Redis 호환)
- **Messaging:** Apache Kafka 4.x (KRaft Mode)
- **Observability:** Micrometer + OpenTelemetry (Loki, Grafana, Tempo, Mimir)
- **Architecture:** MSA, Event-Driven, Multi-Module

*(상세 아키텍처 결정 배경 및 가이드는 옵시디언 문서를 제공예정.)*

## Getting Started

### Prerequisites
- JDK 21 이상 설치
- Docker 및 Docker Compose (통합 인프라 실행용)

### Build
프로젝트의 모든 모듈을 빌드하고 테스트합니다.
```bash
./gradlew clean build
```

## 📝 Roadmap & TODOs
- [x] Gradle Convention Plugin 기반 멀티 모듈 아키텍처 구축
- [ ] API 공통 응답 (`ApiResponse`) 및 예외 처리(`GlobalException`) 구현
- [ ] Http Interface 및 RestClient 기반 내부 통신 표준화
- [ ] Micrometer Tracing OTLP 연동
- [ ] SonarQube 정적 코드 분석 연동
# Modern MMA Platform

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-brightgreen?style=flat-square&logo=springboot)
![Gradle](https://img.shields.io/badge/Gradle-9.x-02303A?style=flat-square&logo=gradle)
![Architecture](https://img.shields.io/badge/Architecture-Modular_Monolith_%2F_Multi--Module-blue?style=flat-square)

> 최신 생태계(Java 21, Spring Boot 3.5)를 기반으로 구축한 **표준 프레임워크 및 플랫폼**입니다.
> 이름의 **MMA**는 **M**odular **M**onolith **A**rchitecture와 **M**ulti-Module **A**rchitecture를 동시에 의미합니다.
> 처음에는 모듈러 모놀리스로 단일 배포하되, 도메인 경계를 모듈 단위로 명확히 분리해두어
> 필요 시 개별 마이크로서비스로 분리 배포할 수 있는 유연한 구조를 지향합니다.

## Why Modular Monolith?

- 초기 단계에서는 운영 복잡도(배포, 모니터링, 서비스 간 통신)를 낮추고 빠르게 개발 속도를 확보합니다.
- 모듈 간 경계(domain / application / infra / web)와 의존성 방향을 처음부터 엄격히 지켜, 나중에 특정 모듈만 떼어내 별도 서비스로 분리하기 쉽게 합니다.
- 공통 횡단 관심사(인증, 예외 처리, 로깅, 캐시 등)는 `framework/` 레이어로 미리 표준화해두어, 모놀리스든 개별 서비스든 동일한 기반 위에서 동작합니다.

## Project Structure

본 프로젝트는 불필요한 의존성 전파를 막고 빌드 속도를 최적화하기 위해 **Gradle Convention Plugins (`build-logic`)** 기반의 멀티 모듈로 구성되어 있습니다.

```text
modern-mma-platform/
 ├── 📁 build-logic/                 # 빌드 컨벤션 플러그인 (의존성/버전 중앙 제어)
 │    ├── java-convention            # Java 21 공통 설정
 │    ├── spring-boot-convention     # Spring Boot 3.5 기본 설정
 │    └── library-convention         # 프레임워크 라이브러리용 (BootJar 비활성화 등)
 │
 ├── 📁 framework/                   # 공통 프레임워크 모듈 (Libraries)
 │    ├── framework-core/            # 글로벌 유틸 및 공통 포맷
 │    ├── framework-response/        # API 공통 응답 규격
 │    ├── framework-exception/       # 예외 타입 및 에러코드 정의 (순수)
 │    ├── framework-web/             # Web MVC, GlobalExceptionHandler, Http Interface
 │    ├── framework-jpa/             # JPA Auditing, BaseEntity
 │    ├── framework-logging/         # 구조화 로그, 상관관계 ID (배치 환경 포함 독립 사용 가능)
 │    ├── framework-observability/   # Tracing + Metrics (framework-logging 기반)
 │    ├── framework-cache/           # Redis(Valkey) 캐시 설정
 │    ├── framework-kafka/           # Kafka Producer/Consumer 공통 설정, DLT 처리
 │    ├── framework-test/            # 모듈 테스트 지원
 │    └── framework-security/        # JWT 발급/검증, Refresh Token Rotation, Spring Security 통합
 │
 ├── 📁 modules/                     # 도메인 모듈 (Modular Monolith 경계 단위)
 │    └── sample-order/              # 샘플 도메인 (예시 개념 수준)
 │         └── src/main/java/.../order/
 │              ├── domain/          # 엔티티, 값객체
 │              ├── application/     # 유스케이스
 │              ├── infra/           # Repository 등 구현체
 │              └── web/             # Controller
 │
 ├── 📁 bootstrap/                   # 단일 실행 진입점 (모든 modules/* 를 합쳐 기동)
 │    └── MmaApplication.java
 │
 └── 📄 build.gradle.kts             # Root Build Script (TODO: SonarQube 등 전역 설정)
```

## Layering & Dependency Rules

상위 계층은 하위 계층에만 의존할 수 있습니다.

```text
application → domain → core
infra       → domain
web         → application
```

다음과 같은 의존은 허용하지 않습니다.

```text
domain → infrastructure
core → framework-web
business → framework-kafka
```

`framework/` 내부 모듈 간에도 동일한 원칙이 적용됩니다. 예를 들어 `framework-exception`은 `framework-web`을 알지 못하며, 그 반대 방향(`framework-web → framework-exception`)만 허용됩니다.

## Core Technology Stack

- **Language:** Java 21 (Virtual Threads 적극 활용)
- **Framework:** Spring Boot 3.5.x
- **Build Tool:** Gradle 9.x (Kotlin DSL)
- **Database:** PostgreSQL 17, Valkey 8 (Redis 호환)
- **Messaging:** Apache Kafka 4.x (KRaft Mode)
- **Auth:** JWT (Access) + Refresh Token Rotation (Redis 저장)
- **Observability:** Micrometer + OpenTelemetry (Loki, Grafana, Tempo, Mimir)
- **Architecture:** Modular Monolith → Multi-Module 전환 대비 구조

*(상세 아키텍처 결정 배경 및 가이드는 옵시디언 문서로 별도 제공 예정.)*

## Getting Started

### Prerequisites
- JDK 21 이상 설치
- Docker 및 Docker Compose (통합 인프라 실행용)

### Build
프로젝트의 모든 모듈을 빌드하고 테스트합니다.
```bash
./gradlew clean build
```

### Run
`bootstrap` 모듈이 모든 `modules/*` 를 합쳐 단일 애플리케이션으로 기동합니다.
```bash
./gradlew :bootstrap:bootRun
```

## 📝 Roadmap & TODOs
- [x] Gradle Convention Plugin 기반 멀티 모듈 아키텍처 구축
- [x] API 공통 응답(`ApiResponse`) 및 예외 처리(`GlobalExceptionHandler`) 구현
- [x] Redis 기반 캐시 및 Refresh Token Rotation을 포함한 인증/인가(`framework-security`) 구현
- [x] Kafka 공통 Producer/Consumer 설정 및 DLT(Dead Letter Topic) 처리
- [ ] `modules/sample-order` 샘플 도메인 구현
- [ ] Http Interface 및 RestClient 기반 모듈 간 통신 표준화
- [ ] Micrometer Tracing OTLP 연동
- [ ] SonarQube 정적 코드 분석 연동

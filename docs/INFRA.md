# 🚀 인프라 및 기술 스택 (Infrastructure & Tech Stack)

이 문서는 '밥심(BabSim)' 프로젝트의 개발, 배포, 운영을 위해 선정된 주요 기술 스택과 도구를 정의합니다.

---

## 1. 데이터베이스 (Database)

- **RDBMS**: `MySQL`
  - **설명**: AWS RDS를 통해 관리되며, 사용자의 기본 정보, 식단 기록 등 정형 데이터를 저장합니다.
  - **연결 정보**: `application.yml`에 정의되어 있으며, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` 환경 변수를 통해 주입됩니다.

- **NoSQL**: `Redis`
  - **설명**: AWS ElastiCache를 통해 관리되며, JWT 토큰, 캐시 데이터 등 빠른 조회가 필요한 비정형 데이터를 처리합니다.
  - **연결 정보**: `application.yml`에 정의되어 있으며, `REDIS_HOST`, `REDIS_PORT` 환경 변수를 통해 주입됩니다.

- **추후 고려사항**: `시계열 데이터베이스 (Time-Series Database)`
  - **목적**: 사용자의 장기적인 건강 데이터(영양 섭취 추이 등)를 효율적으로 저장하고 분석하기 위해 도입을 고려할 수 있습니다. (예: InfluxDB)

---

## 2. CI/CD 및 클라우드 (CI/CD & Cloud)

- **CI/CD**: `Jenkins`
  - **설명**: `Jenkinsfile`에 정의된 파이프라인을 통해 코드 체크아웃, 빌드, 테스트, Docker 이미지 빌드 및 배포를 자동화합니다.

- **Cloud**: `AWS (Amazon Web Services)`
  - **EC2 (Elastic Compute Cloud)**: 애플리케이션 서버(Docker 컨테이너)를 호스팅합니다.
  - **RDS (Relational Database Service)**: `MySQL` 데이터베이스를 관리합니다.
  - **ECR (Elastic Container Registry)**: 빌드된 `Docker` 이미지를 저장하고 관리합니다.
  - **S3 (Simple Storage Service)**: 사용자가 업로드하는 음식 사진 등 정적 파일을 저장합니다.

- **Containerization**: `Docker`
  - **설명**: `Dockerfile`을 통해 애플리케이션을 컨테이너화하여, 개발 및 배포 환경의 일관성을 보장합니다.

---

## 3. 웹 및 보안 (Web & Security)

- **Web Server**: `Nginx`
  - **역할**: 리버스 프록시(Reverse Proxy)로 동작하며, 클라이언트의 요청을 백엔드 애플리케이션으로 전달합니다.
  - **주요 기능**: SSL/TLS 암호화, 로드 밸런싱, HTTP 요청을 HTTPS로 리디렉션합니다.

- **Security**:
  - **SSL/TLS**: `Let's Encrypt` 등을 통해 발급된 인증서를 `Nginx`에 적용하여 모든 통신을 암호화합니다.
  - **Spring Security**: JWT(JSON Web Token) 기반의 인증 및 인가 로직을 구현하여 API 엔드포인트를 보호합니다.

---

## 4. 모니터링 및 문서화 (Monitoring & Documentation)

- **Monitoring**: `Grafana`, `Prometheus`
  - **Prometheus**: Spring Boot Actuator를 통해 노출된 애플리케이션 메트릭(CPU 사용량, JVM 상태 등)을 주기적으로 수집합니다.
  - **Grafana**: `Prometheus`가 수집한 데이터를 시각화하여 대시보드를 구축하고, 시스템 상태를 실시간으로 모니터링합니다.

- **API Document**: `Swagger (OpenAPI)`
  - **설명**: 코드에 작성된 어노테이션을 기반으로 API 명세를 자동으로 생성하고, `/swagger-ui.html` 경로를 통해 테스트 가능한 UI를 제공합니다.

---

## 5. 개발 생산성 (Developer Productivity)

- **Review AI**: `CodeRabbit`
  - **설명**: GitHub Pull Request에 연동되어, 코드 변경 사항에 대한 자동 리뷰 및 개선 제안을 제공하여 코드 품질을 향상시킵니다.

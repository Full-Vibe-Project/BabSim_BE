# ✅ AI 프롬프트 성공/실패 로그

*AI의 프롬프트 작업 성공/실패 내역을 이 문서에 기록합니다.*

---

## 🗓️ 2025-10-10

- **Prompt**: 현재 application.yml과 application-dev.yml을 확인하면, DB와 Redis 설정이 단순히 환경 변수 형태로 작성되어 있는데, 먼저 application.yml에서 DB를 H2가 아닌 dev와 같이 MySQL을 사용하도록 바꿔줘. 그리고 docker-compose.yml 파일을 하나 두어서 각 환경에서 도커 컨테이너로 실행하고 로컬에서 사용하는 application-local.yml파일을 추가해서 해당 도커 컨테이너에서 실행되는 db와 연결되도록 구현해줘. 그리고 application.yml은 기본적으로 local에 연결되도록 하고, 이후에 dev, prod는 상황에 따라 지정할 거니까 그렇게 동작할 수 있도록 설정해줘.
- **Result**: **Success**
- **Details**:
    1. `application.yml`을 수정하여 기본 프로필을 `local`로 지정하고 H2 관련 설정을 제거함.
    2. 로컬 Docker 환경(MySQL, Redis)에 연결하기 위한 `application-local.yml` 파일을 생성함.
    3. `mysql` 및 `redis` 서비스를 정의하는 `docker-compose.yml` 파일을 프로젝트 루트에 생성함.
    4. 로컬 개발 환경이 `docker-compose up` 명령어를 통해 쉽게 구성될 수 있도록 설정 완료.


- **Prompt**: 애플리케이션의 상태를 확인할 수 있는 Health Check API를 추가해줘.
- **Result**: **Success**
- **Details**:
    1. `common` 패키지 내에 `controller` 패키지를 생성함.
    2. `HealthCheckController.java`를 생성하여 `/health` 경로로 GET 요청 시 "OK"를 반환하는 API 엔드포인트를 추가함.
    3. Swagger 어노테이션을 추가하여 API 명세에 해당 엔드포인트가 포함되도록 함.


- **Prompt**: 현재 파일에서, username과 password를 환경 변수로 설정하려고 해. 코드를 수정해줘.
- **Result**: **Success**
- **Details**:
    1. `application-local.yml` 파일의 `datasource` 설정을 수정함.
    2. `username`과 `password`를 각각 `${DB_USERNAME:babsim}`과 `${DB_PASSWORD:babsimpassword}`로 변경함.
    3. 이를 통해 환경 변수가 존재하면 해당 값을 사용하고, 없으면 기본값을 사용하도록 하여 유연성과 보안을 강화함.


- **Prompt**: docker-compose 파일 또한, MYSQL_USER와 MYSQL_PASSWORD, MYSQL_ROOT_PASSWORD 값을 환경 변수화하도록 해줘
- **Result**: **Success**
- **Details**:
    1. `docker-compose.yml` 파일의 `mysql` 서비스 `environment` 설정을 수정함.
    2. `MYSQL_USER`, `MYSQL_PASSWORD`, `MYSQL_ROOT_PASSWORD` 값을 각각 `${MYSQL_USER:-babsim}`, `${MYSQL_PASSWORD:-babsimpassword}`, `${MYSQL_ROOT_PASSWORD:-rootpassword}`로 변경함.
    3. 이를 통해 `.env` 파일 또는 시스템 환경 변수로 Docker 컨테이너의 민감한 정보를 관리할 수 있도록 보안을 강화함.

---

## 🗓️ 2025-10-15

- **Prompt**: 아래의 수정된 프로젝트 기획서 내용을 바탕으로, docs/PRD.md 파일에서 개선해야 할 부분이 있으면 개선하여 최신화해줘.
- **Result**: **Success**
- **Details**:
    1. 새로운 기획서 내용을 반영하여 `docs/PRD.md` 파일을 전면 개정함.
    2. 경쟁 서비스 분석, 구체화된 사용자 플로우, 초기 데이터베이스 설계(테이블명 영문화 포함) 등 상세 내용을 추가하여 문서의 완성도를 높임.
    3. 문서 버전을 1.1로 업데이트하고 최종 수정일을 반영함.


- **Prompt**: 현재 브랜치에서의 개발 목적은, 아래와 같아. 개발 목적을 읽고, 현재 파일에서 명시된 테스트 규칙을 기반으로 하여 TDD 방식으로 개발을 진행해줘. [개발 목적] - 최신화된 PRD를 기반으로 음식 엔티티를 적절하게 최신화하고, 관련 CRUD API를 구현
- **Result**: **Success**
- **Details**:
    1. **TDD(Test-Driven Development)** 방법론에 따라 `Red-Green-Refactor` 사이클을 준수하며 개발을 진행함.
    2. **Repository, Service, Controller** 각 계층별로 실패하는 테스트를 먼저 작성하고, 이를 통과시키는 코드를 구현하여 Food 도메인의 CRUD API를 완성함.


- **Prompt**: 현재 테스트코드를 실행했을 때, ControllerTest와 RepositoryTest는 통과하지 못하고, ServiceTest만 통과하는데 문제점을 파악하고 개선해줘.
- **Result**: **Success**
- **Details**:
    1. **문제 파악**: 테스트 컨텍스트 로딩 시, 불필요한 Redis 설정과 Security 설정을 불러오려다 실패하는 것을 확인함.
    2. **문제 해결**: `FoodRepositoryTest`에는 `@TestPropertySource`를 추가하여 캐시 설정을 비활성화하고, `FoodControllerTest`에는 `@WebMvcTest`의 `excludeAutoConfiguration` 속성을 사용하여 보안 설정을 제외함으로써 모든 테스트가 통과하도록 수정함.


- **Prompt**: 현재 domain/dto 내부의 디렉토리 구조를 보면, request와 response 디렉토리로 dto를 구분해두었는데 그와 별개로 FoodCreateRequestDto와 같이 dto 바로 아래에 파일이 생성되어있고 기존의 dto 파일명과도 규칙이 다른 것 같아. ai-guidelines 및 conventions/02-backend에서 정의된 디렉토리 구조 규칙을 다시 확인해 보고 하나로 통일해줘.
- **Result**: **Success**
- **Details**:
    1. `auth` 도메인의 DTO 구조를 분석하여 `dto/request`, `dto/response` 디렉토리 구조와 네이밍 컨벤션을 재확인함.
    2. `diet` 도메인의 `Food` 관련 DTO들을 규칙에 맞게 `request`, `response` 디렉토리로 이동하고 파일명을 변경함 (`FoodCreateRequestDto` -> `FoodCreateRequest`).
    3. 변경된 DTO 위치와 이름에 따라 `Controller`, `Service`, `Entity`, 테스트 코드의 `import` 구문을 모두 수정하여 리팩토링을 완료함.

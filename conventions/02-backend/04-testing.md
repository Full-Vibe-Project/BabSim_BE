# Java Spring 애플리케이션 테스트 코드 작성 원칙

이 문서는 AI 기반 코딩 도구(예: Vibe Code, GitHub Copilot 등)가 Java Spring 애플리케이션의 테스트 코드를 작성할 때 참고할 수 있는 **테스트 작성 지침**을 제공합니다.

## 1. 테스트 구조 및 패키지

1. **테스트 패키지**
    - 실제 코드와 동일한 패키지 구조 아래 `src/test/java`에 위치시킵니다.
        - 예시: `src/main/java/com/example/service/UserService.java` → `src/test/java/com/example/service/UserServiceTest.java`
2. **클래스 네이밍**
    - 테스트 클래스명은 대상 클래스명 뒤에 `Test`를 붙입니다.
        - 예시: `UserService` → `UserServiceTest`
3. **메서드 네이밍**
    - `given[조건]_when[행동]_then[결과]` 패턴 사용
        - 예시: `givenValidUserId_whenFindUser_thenReturnUser()`

## 2. 테스트 유형

1. **단위(Unit) 테스트**
    - Spring Context 로딩 없이, Mockito로 외부 의존(서비스, 리포지토리) 주입
    - 예시: `@ExtendWith(MockitoExtension.class)`
2. **통합(Integration) 테스트**
    - Spring Boot Test 환경에서 실제 컨텍스트 로딩
    - 예시: `@SpringBootTest`, `@AutoConfigureMockMvc`
3. **웹 레이어 테스트**
    - 컨트롤러 단위 MockMvc 사용
    - 예시: `@WebMvcTest(UserController.class)`
4. **데이터 액세스 테스트**
    - JPA 리포지토리 검증 시 `@DataJpaTest`
    - H2 인메모리 DB 사용 권장

## 3. 어노테이션 가이드

| 테스트 유형 | 어노테이션 |
| --- | --- |
| 단위 테스트 | `@ExtendWith(MockitoExtension.class)` |
| 통합 테스트 | `@SpringBootTest`, `@ActiveProfiles("test")` |
| 웹 레이어 테스트 | `@WebMvcTest(Controller.class)`, `@AutoConfigureMockMvc` |
| JPA 테스트 | `@DataJpaTest` |
| 설정 치환 | `@TestPropertySource(locations = "classpath:application-test.yml")` |

## 4. 목(Mock) 및 주입

1. **Mockito** 사용
    
        ```java
        @ExtendWith(MockitoExtension.class)class UserServiceTest {  @Mock  private UserRepository userRepository;  @InjectMocks  private UserService userService;  @Test  void givenValidId_whenFind_thenUserReturned() {    User user = new User(1L, "alice");    when(userRepository.findById(1L)).thenReturn(Optional.of(user));    User result = userService.findUser(1L);    assertThat(result.getName()).isEqualTo("alice");  }}
        ```
    
2. **Testcontainers** 활용(통합 테스트)
    
         ```java
       
         @SpringBootTest@ActiveProfiles("test")@Testcontainersclass UserRepositoryTest {  @Container  static PostgreSQLContainer<?> postgres =
              new PostgreSQLContainer<>("postgres:13")      .withDatabaseName("testdb");  @Autowired  private UserRepository userRepository;  @Test  void whenSaveUser_thenFound() {    User user = new User(null, "bob");    userRepository.save(user);    assertThat(userRepository.findByName("bob")).isNotEmpty();  }}
      
          ```
    

## 5. Assertions

- **AssertJ** 사용 권장 (`assertThat()`) for 가독성 좋은 체이닝
- 예시:
    
      ```java
      assertThat(list).hasSize(3)                .extracting(User::getName)                .containsExactly("a","b","c");
      ```
    

## 6. 테스트 데이터 관리

1. **Fixture**
    - 테스트마다 독립적인 데이터 생성
    - 공통 객체는 `@BeforeEach`에서 초기화
2. **외부 파일**
    - 테스트용 JSON은 `src/test/resources/fixtures/`에 저장
    - `ObjectMapper`로 로드하여 검증

## 7. CI 연계 및 커버리지

- **Jacoco** 설정으로 커버리지 측정
- `build.gradle`:
    
      ```groovy
      jacoco {  toolVersion = "0.8.8"}test {  finalizedBy jacocoTestReport
      }jacocoTestReport {  reports {    xml.required = true    html.required = true  }}
      ```
    
- CI 파이프라인에서 `./gradlew test jacocoTestReport` 실행 후, **커버리지 80%** 이상 기준 설정

## 8. 지침 요약

1. 테스트 클래스 및 메서드 네이밍 표준 준수
2. 단위 vs 통합 테스트 구분하여 알맞은 어노테이션 사용
3. Mockito & Testcontainers로 외부 의존 격리
4. AssertJ로 명확한 검증
5. Fixture 및 리소스 파일로 테스트 독립성 확보
6. CI에서 커버리지 검증 포함

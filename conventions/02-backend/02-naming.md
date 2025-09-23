# 네이밍 규칙 (Naming Convention)

이 프로젝트의 Java 네이밍 규칙은 [네이버 사내 Java 코드 컨벤션](https://naver.github.io/hackday-conventions-java/)을 따릅니다.
자세한 내용은 [03-coding-style.md](./03-coding-style.md) 문서를 참고합니다.

## 요약

| 구분 | 규칙 | 예시 |
|---|---|---|
| **패키지** | 소문자, 단어 구분 없음 | `com.babsim.apigateway` |
| **클래스/인터페이스** | 대문자 카멜 표기법 (PascalCase) | `class UserProfile` |
| **메서드** | 소문자 카멜 표기법 (lowerCamelCase) | `getUserName()` |
| **상수** | 대문자 스네이크 케이스 (UPPER_SNAKE_CASE) | `static final int MAX_USERS = 100;` |
| **변수** | 소문자 카멜 표기법 (lowerCamelCase) | `String userName;` |
| **테스트 클래스** | 대상 클래스명 + `Test` | `UserServiceTest` |

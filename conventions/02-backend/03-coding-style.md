# NAVER Java Coding Convention

v1.2.0, 2020.07.24

## 1. 파일 공통 요건
### 1.1. 파일 인코딩은 UTF-8
[encoding-utf8]

모든 소스, 텍스트 문서 파일의 인코딩은 UTF-8로 통일한다.

### 1.2. 새줄 문자는 LF
[newline-lf]

Unix 형식의 새줄 문자(newline)인 LF(Line Feed, 0x0A)을 사용한다. Windows 형식인 CRLF가 섞이지 않도록 편집기와 GIT 설정 등을 확인한다.

### 1.3. 파일의 마지막에는 새줄
[newline-eof]

파일의 마지막은 새줄 문자 LF로 끝나야 한다.

---

## 2. 이름 (Naming)
### 2.1. 식별자에는 영문/숫자/언더스코어만 허용
[identifier-char-scope]

변수명, 클래스명, 메서드명 등에는 영어와 숫자만을 사용한다. 상수에는 단어 사이의 구분을 위하여 언더스코어(_)를 사용한다. 정규표현식 `[^A-Za-z0-9_]`에 부합해야 한다.

### 2.2. 한국어 발음대로의 표기 금지
[avoid-korean-pronounce]

식별자의 이름을 한글 발음을 영어로 옮겨서 표기하지 않는다. 한국어 고유명사는 예외이다.

- 나쁜 예 : `moohyungJasan` (무형자산)
- 좋은 예 : `intangibleAssets` (무형자산)

### 2.3. 대문자로 표기할 약어 명시
[list-uppercase-abbr]

클래스명, 변수명에 쓰일 단어 중 모든 글자를 대문자로 표기할 약어의 목록을 프로젝트별로 명시적으로 정의한다.

### 2.4. 패키지 이름은 소문자로 구성
[package-lowercase]

패키지 이름은 소문자를 사용하여 작성한다. 단어별 구문을 위해 언더스코어(_)나 대문자를 섞지 않는다.

- 나쁜 예: `package com.navercorp.apiGateway`
- 좋은 예: `package com.navercorp.apigateway`

### 2.5. 클래스/인터페이스 이름에 대문자 카멜표기법 적용
[class-interface-lower-camelcase]

클래스 이름은 단어의 첫 글자를 대문자로 시작하는 대문자 카멜표기법(Upper camel case)을 사용한다.

- 좋은 예: `public class Reservation`, `public class AccessToken`

### 2.6. 클래스 이름에 명사 사용
[class-noun]

클래스 이름은 명사나 명사절로 짓는다.

### 2.7. 인터페이스 이름에 명사/형용사 사용
[interface-noun-adj]

인터페이스(interface)의 이름은 클래스 이름은 명사/명사절로 혹은 형용사/형용사절로 짓는다.

- 좋은 예: `public interface RowMapper`, `public interface AutoClosable`

### 2.8. 테스트 클래스는 'Test’로 끝남
[test-class-suffix]

JUnit 등으로 작성한 테스트 코드를 담은 클래스는 'Test’을 마지막에 붙인다.

- 좋은 예: `public class WatcherTest`

### 2.9. 메서드 이름에 소문자 카멜표기법 적용
[method-lower-camelcase]

메서드의 이름에는 첫 번째 단어를 소문자로 작성하고, 이어지는 단어의 첫 글자를 대문자로 작성하는 소문자 카멜표기법(Lower camel case)를 사용한다.

### 2.10. 메서드 이름은 동사/전치사로 시작
[method-verb-preposition]

메서드명은 기본적으로는 동사로 시작한다. 다른 타입으로 전환하는 메서드나 빌더 패턴을 구현한 클래스의 메서드에는 전치사를 쓸 수 있다.

- 좋은 예: `renderHtml()`, `toString()`, `withUserId(String id)`

### 2.11. 상수는 대문자와 언더스코어로 구성
[constant_uppercase]

상태를 가지지 않는 자료형이면서 static final`로 선언되어 있는 필드일 때를 상수로 간주한다. 상수 이름은 대문자로 작성하며, 복합어는 언더스코어(`_`)를 사용하여 단어를 구분한다.

- 좋은 예: `public final int UNLIMITED = -1;`

### 2.12. 변수에 소문자 카멜표기법 적용
[var-lower-camelcase]

상수가 아닌 클래스의 멤버변수/지역변수/메서드 파라미터에는 소문자 카멜표기법(Lower camel case)을 사용한다.

- 좋은 예: `private boolean authorized;`

### 2.13. 임시 변수 외에는 1 글자 이름 사용 금지
[avoid-1-char-var]

메서드 블럭 범위 이상의 생명 주기를 가지는 변수에는 1글자로 된 이름을 쓰지 않는다. 반복문의 인덱스나 람다 표현식의 파라미터 등 짧은 범위의 임시 변수에는 관례적으로 1글자 변수명을 사용할 수 있다.

---

## 3. 선언 (Declarations)

### 3.1. 소스파일당 1개의 탑레벨 클래스를 담기
[1-top-level-class]

탑레벨 클래스(Top level class)는 소스 파일에 1개만 존재해야 한다.

### 3.2. static import에만 와일드 카드 허용
[avoid-star-import]

클래스를 import할때는 와일드카드(*) 없이 모든 클래스명을 다 쓴다. static import에서는 와일드카드를 허용한다.

### 3.3. 제한자 선언의 순서
[modifier-order]

클래스/메서드/멤버변수의 제한자는 Java Language Specification에서 명시한 아래의 순서로 쓴다.
`public protected private abstract static final transient volatile synchronized native strictfp`

### 3.4. 애너테이션 선언 후 새줄 사용
[newline-after-annotation]

클래스, 인터페이스, 메서드, 생성자에 붙는 애너테이션은 선언 후 새줄을 사용한다.

### 3.5. 한 줄에 한 문장
[1-state-per-line]

문장이 끝나는 ; 뒤에는 새줄을 삽입한다. 한 줄에 여러 문장을 쓰지 않는다.

### 3.6. 하나의 선언문에는 하나의 변수만
[1-var-per-declaration]

변수 선언문은 한 문장에서 하나의 변수만을 다룬다.

### 3.7. 배열에서 대괄호는 타입 뒤에 선언
[array-square-after-type]

배열 선언에 오는 대괄호([])는 타입의 바로 뒤에 붙인다.

### 3.8. `long`형 값의 마지막에 `L`붙이기
[long-value-suffix]

long형의 숫자에는 마지막에 대문자 'L’을 붙인다.

### 3.9. 특수 문자의 전용 선언 방식을 활용
[special-escape]

`\b, \f, \n,\r,\t, \`, `\"` 와 같이 특별히 정의된 선언 방식이 있는 특수 문자는 전용 방식을 활용한다.

---

## 4. 들여쓰기 (Indentation)

### 4.1. 하드탭 사용
[indentation-tab]

탭(tab) 문자를 사용하여 들여쓴다. 탭 대신 스페이스를 사용하지 않는다.

### 4.2. 탭의 크기는 4개의 스페이스
[4-spaces-tab]

1개의 탭의 크기는 스페이스 4개와 같도록 에디터에서 설정한다.

### 4.3. 블럭 들여쓰기
[block-indentation]

클래스, 메서드, 제어문 등의 코드 블럭이 생길 때마다 1단계를 더 들여쓴다.

---

## 5. 중괄호 (Braces)

### 5.1. K&R 스타일로 중괄호 선언
[braces-knr-style]

중괄호 선언은 K&R 스타일(Kernighan and Ritchie style)을 따른다. 줄의 마지막에서 시작 중괄호`{`를 쓰고 열고 새줄을 삽입한다. 블럭을 마친후에는 새줄 삽입 후 중괄호를 닫는다.

### 5.2. 닫는 중괄호와 같은 줄에 else, catch, finally, while 선언
[sub-flow-after-brace]

`else`, `catch`, `finally`, do-while문의 `while`은 닫는 중괄호(}) 와 같은 줄에 쓴다.

### 5.3. 빈 블럭에 새줄 없이 중괄호 닫기 허용
[permit-concise-empty-block]

내용이 없는 블럭을 선언할 때는 같은 줄에서 중괄호를 닫는 것을 허용한다. (예: `public void close() {}`)

### 5.4. 조건/반복문에 중괄호 필수 사용
[need-braces]

조건, 반복문이 한 줄로 끝나더라도 중괄호를 활용한다.

---

## 6. 줄바꿈 (Line-wrapping)

### 6.1. 최대 줄 너비는 120
[line-length-120]

최대 줄 사용 너비는 120자까지 가능하다.

### 6.2. package,import 선언문은 한 줄로
[1-line-package-import]

package,import 선언문 중간에서는 줄을 바꾸지 않는다.

### 6.3. 줄바꿈 후 추가 들여쓰기
[indentation-after-line-wrapping]

줄바꿈 이후 이어지는 줄에서는 최초 시작한 줄에서보다 적어도 1단계의 들여쓰기를 더 추가한다.

### 6.4. 줄바꿈 허용 위치
[line-wrapping-position]

가독성을 위해 줄을 바꾸는 위치는 다음 중의 하나로 한다.
- `extends`, `implements`, `throws` 선언 후
- 시작 소괄호 `(`, 콤마 `,` 후
- 점 `.` 전
- 연산자 전

---

## 7. 빈 줄(Blank lines)

### 7.1. package 선언 후 빈 줄 삽입
[blankline-after-package]

`package` 선언 후 `import` 선언 전에 빈 줄을 삽입한다.

### 7.2. import 선언의 순서와 빈 줄 삽입
[import-grouping]

import 구절은 아래와 같은 순서로 그룹을 묶고, 각 그룹 사이에 빈 줄을 삽입한다.
1. static imports
2. `java.*`
3. `javax.*`
4. `org.*`
5. `net.*`
6. `com.*` (네이버/NHN 제외)
7. 기타
8. `com.nhncorp.*`
9. `com.navercorp.*`
10. `com.naver.*`

### 7.3. 메소드 사이에 빈 줄 삽입
[blankline-between-methods]

메서드 선언 사이에 빈 줄을 삽입한다.

---

## 8. 공백 (Whitespace)

### 8.1. 공백으로 줄을 끝내지 않음
[no-trailing-spaces]

모든 줄은 탭이나 공백으로 끝내지 않는다.

### 8.2. 제어문 키워드와 여는 소괄호 사이에 공백 삽입
[space-between-keyword-parentheses]

`if, for, while, catch, synchronized, switch`와 같은 제어문 키워드 뒤에 오는 여는 소괄호 `(` 앞에 공백을 삽입한다.

### 8.3. 식별자와 여는 소괄호 사이에 공백 미삽입
[no-space-between-identifier-parentheses]

메서드/생성자 호출 및 선언 시, 이름과 여는 소괄호 `(` 사이에는 공백을 삽입하지 않는다.

### 8.4. 콤마/구분자 세미콜론의 뒤에만 공백 삽입
[space-after-comma-semicolon]

콤마(`,`)와 반복문의 구분자 세미콜론(;) 뒤에만 공백을 삽입한다.

### 8.5. 이항/삼항 연산자의 앞 뒤에 공백 삽입
[space-around-binary-ternary-operator]

이항/삼항 연산자의 앞뒤에는 공백을 삽입한다.

### 8.6. 단항 연산자와 연산 대상 사이에 공백을 미삽입
[no-space-increament-decrement-operator]

단항 연산자(++, --, !, ~)와 연산 대상 사이에는 공백을 삽입하지 않는다.

### 8.7. 주석문 기호 전후의 공백 삽입
[space-around-comment]

- 한 줄 주석 `//` 앞과 뒤에 공백을 하나씩 삽입한다.
- 블록 주석 `/*` 뒤와 `*/` 앞에 공백을 하나씩 삽입한다.

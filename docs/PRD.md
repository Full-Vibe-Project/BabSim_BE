# ✅ 제품 요구사항 정의서 (PRD) - 헬스케어 식단 관리 서비스

> 이 문서는 'AI 기반 헬스케어 식단 관리 서비스'의 제품 요구사항을 정의합니다. 모든 팀원(AI 포함)은 이 문서를 기준으로 기능 개발, 테스트, 문서화를 진행합니다.

**문서 버전: 1.1**
**최종 수정일: 2025-10-15**

---

## 1. 프로젝트 개요 (Project Overview)

### 1.1. 프로젝트명
- **헬스케어 식단 관리 서비스** (가칭: 밥심)

### 1.2. 목표
- AI 기반의 자동화된 식단 기록, 영양 분석, 맞춤형 추천을 통해 사용자의 건강 관리 효율을 극대화한다.

### 1.3. 타겟 사용자
- **주요 타겟**: 건강한 식습관을 만들고자 하는 일반인
- **확장 타겟**: 당뇨, 고혈압 등 **질병 관리가 필요한 사용자**

### 1.4. 경쟁 서비스 분석
- **밀리그램**: 식단, 운동, 체중 기록 및 공유 서비스.
  - 장점: 영양 관리 기능 제공.
  - 단점: 모든 정보를 수동으로 입력해야 하는 번거로움.
- **다이어트 카메라**: 음식 사진 기반 칼로리 계산 서비스.
  - 장점: 칼로리 및 영양 정보 기록 가능.
  - 단점: 기록 이후의 구체적인 행동 지침(Action Item)이 부족.
- **다이어트 신**: 다이어트 중심의 식단 및 운동 관리 서비스.
- **인아웃**: 칼로리 계산 및 다이어트 기록 앱.
- **Unimeal**: 개인화된 식단을 제안하는 서비스.
- **greating**: 특정 목적(예: 당뇨 관리)에 맞는 식단을 정기 구독하는 서비스.
- **맛있져염**: 저염식 등 맞춤 식단을 판매하는 쇼핑몰.

---

## 2. 문제 정의 (Problem)

- **지속성의 어려움**: 식단 기록은 번거롭고 귀찮아 꾸준히 지속하기 어렵다. 이는 결국 건강 관리 습관화의 실패로 이어진다.
- **개인화의 한계**: 기존 앱들은 대부분 단순 칼로리 계산에만 집중하여, 개인의 질병이나 특성을 고려한 맞춤형 관리에 한계가 있다.
- **데이터 활용 부족**: 사용자의 건강 데이터가 축적되더라도 이를 활용하여 장기적인 건강 리스크를 예측하고 예방하는 기능이 부족하다.

---

## 3. 솔루션 (Solution)

**"AI 기반 헬스케어 식단 관리 플랫폼"**

- **간편한 프로필 설정**: 나이, 체중, 키 등 기본 정보와 함께 질병, 알레르기 등 건강 상태를 입력하여 개인화 추천의 기반으로 삼는다.
  - *(확장 기능)* 건강검진 결과 API 연동을 통한 데이터 자동화.
- **식단 기록 자동화**: 음식 사진을 인식하여 자동으로 식단을 기록하고, 식약처 음식 DB를 활용하여 정확한 영양 정보를 제공한다. 수기 입력 및 수정 기능도 지원한다.
- **심층적인 영양 분석**: 일/주/월 단위로 영양 성분 섭취량을 분석하고 시각화된 리포트를 제공한다.
- **AI 맞춤형 추천**: 사용자의 프로필과 최근 식단 데이터를 종합하여 부족하거나 과잉인 영양소를 기반으로 맞춤 메뉴를 제안하고, 건강 상태에 따라 피해야 할 음식을 안내한다.
- **Notion 자동 연동**: 매일의 식단 기록과 분석 리포트를 사용자의 Notion 페이지에 자동으로 아카이빙하여 데이터 활용성과 사용자 경험을 극대화한다.

---

## 4. 서비스 주요 플로우 (User Flow)

*(사용자 여정 이미지: nyamnyam_flow_presentation_resized.png)*

### 4.1. 회원가입 & 프로필 설정 (Onboarding)
- **기능**:
  - 기본 정보(나이, 성별, 키, 몸무게) 및 건강 관련 정보(질병, 알레르기) 입력.
  - 목표 설정(체중 감량/증량, 혈당 관리, 일반 건강 관리).
- **필요 요소**:
  - 사용자 프로필 DB 설계.
  - *(선택)* 건강검진 결과 API 연동.

### 4.2. 식단 기록 (Logging)
- **기능**:
  - 사진 업로드 시 AI가 음식을 자동 인식하고 영양소를 추출.
  - 사용자가 AI 인식 결과를 직접 수정/보완 가능 (수기 입력 지원).
  - 아침/점심/저녁/간식으로 구분하여 기록.
- **필요 요소**:
  - 음식 이미지 인식 모델.
  - 영양 성분 DB (식약처 DB 활용).

### 4.3. 정기적 영양 리포트 (Reporting)
- **기능**:
  - 일/주/월 단위 영양 리포트 자동 생성.
  - 부족/과잉 영양소를 시각화된 차트와 그래프로 제공.
  - 설정된 목표 대비 섭취 현황을 보여주는 대시보드.
- **필요 요소**:
  - 데이터 시각화 라이브러리.
  - PDF 또는 Notion 페이지 자동 생성 기능.

### 4.4. 맞춤형 추천 (Recommendation)
- **기능**:
  - 사용자 프로필(질병, 목표 등)과 최근 식단 데이터를 기반으로 맞춤 메뉴 제안.
  - 건강 상태에 따른 금지/주의 음식 알림 (예: 당뇨 환자에게 고당 지수 음식 경고).
- **필요 요소**:
  - 추천 알고리즘 (규칙 기반 + AI 보정).
  - 음식 카테고리 및 레시피 DB.

### 4.5. 기록 관리 & 지속성 강화 (Engagement)
- **기능**:
  - 매일의 식단 기록 및 분석 결과를 Notion에 자동으로 업데이트.
  - 식사 기록을 독려하는 리마인더 및 건강 관리 팁 알림.
  - 꾸준한 기록에 대한 보상 시스템 (뱃지, 포인트 등 Gamification).
- **필요 요소**:
  - Notion API 연동 모듈.
  - 푸시 알림 시스템.

### 4.6. 리포트 & 인사이트 (Advanced)
- **기능**:
  - 장기적인 영양 섭취 패턴을 분석하여 비만, 당뇨 등 잠재적 건강 리스크 예측.
  - 사용자가 직접 입력한 체중, 혈압, 혈당 등의 변화 추적.
  - 상세 분석 내용을 담은 PDF 리포트 자동 생성.
- **필요 요소**:
  - 장기 데이터 분석 모델.
  - PDF 생성 모듈.

---

## 5. 초기 데이터베이스 설계 (Initial DB Design)

*(ERD 이미지: erd.png)*

```sql
-- 건강 상태
CREATE TABLE `health_condition` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(100) NOT NULL,
    `type` ENUM('DISEASE', 'ALLERGY') NOT NULL,
    PRIMARY KEY (`id`)
);

-- 사용자
CREATE TABLE `user` (
    `id` CHAR(36) NOT NULL, -- UUID
    `name` VARCHAR(50),
    `age` INT,
    `sex` CHAR(1),
    `height` DECIMAL(5,2),
    `weight` DECIMAL(5,2),
    `goal` ENUM('WEIGHT_LOSS','WEIGHT_GAIN','BLOOD_SUGAR','GENERAL'),
    `notion_access_token` VARCHAR(255),
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

-- 사용자-건강상태 (다대다 관계 테이블)
CREATE TABLE `user_health_condition` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `user_id` CHAR(36) NOT NULL,
    `health_condition_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    FOREIGN KEY (`health_condition_id`) REFERENCES `health_condition`(`id`)
);

-- 음식
CREATE TABLE `food` (
    `code` VARCHAR(30) NOT NULL,
    `name` VARCHAR(100),
    `energy` DECIMAL(6,2),
    `carb` DECIMAL(6,2),
    `protein` DECIMAL(6,2),
    `fat` DECIMAL(6,2),
    `weight` VARCHAR(20),
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`code`)
);

-- 식단
CREATE TABLE `meal` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `user_id` CHAR(36) NOT NULL,
    `type` ENUM('BREAKFAST','LUNCH','DINNER','SNACK'),
    `image_url` VARCHAR(255),
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
);

-- 식단-음식 (다대다 관계 테이블)
CREATE TABLE `meal_food` (
    `id` BIGINT AUTO_INCREMENT NOT NULL,
    `meal_id` BIGINT NOT NULL,
    `food_code` VARCHAR(30) NOT NULL,
    `quantity` INT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`meal_id`) REFERENCES `meal`(`id`),
    FOREIGN KEY (`food_code`) REFERENCES `food`(`code`)
);
```
- *참고: 테이블 및 컬럼명을 한글에서 영어로 변경하여 적용했습니다.*

---

## 6. 비기능적 요구사항 (Non-Functional Requirements)

- **보안 (Security)**
  - 모든 사용자 비밀번호는 단방향 암호화(BCrypt)하여 저장한다.
  - API 서버와 클라이언트 간의 모든 통신은 TLS/SSL을 통해 암호화한다.
  - 인증/인가가 필요한 모든 API는 JWT를 통해 보호된다.
- **성능 (Performance)**
  - 모든 API의 평균 응답 시간은 500ms 이하를 목표로 한다.
  - AI 분석과 같이 오래 걸리는 작업은 비동기로 처리하여 사용자 경험을 저해하지 않도록 한다.
- **데이터 관리 (Data Management)**
  - 사용자의 개인정보 및 건강 데이터는 관련 법규를 준수하여 안전하게 관리한다.
  - 식약처 제공 음식 영양성분 DB를 초기 데이터로 구축하며, 주기적으로 업데이트한다.
- **확장성 (Scalability)**
  - 시스템은 도메인 중심 아키텍처를 기반으로 설계되어, 새로운 기능(도메인)을 독립적으로 추가하기 용이해야 한다.
  - AI 서비스, 외부 API, 파일 저장소 등은 인터페이스 기반으로 설계하여 다른 구현체로 쉽게 교체할 수 있어야 한다.

---

## 7. 릴리즈 계획 (Roadmap)

- *추후 회의를 통해 구체화 예정*

---

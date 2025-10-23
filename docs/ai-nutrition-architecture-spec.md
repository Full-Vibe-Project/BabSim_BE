> DB 아키텍처 · 데이터 스키마 · 전체 흐름 정리
>

---

## 🧭 1. 시스템 개요

**목표**

사용자의 **식단 기록**, **영양 상태 변화**, **건강 목표**를 바탕으로

AI가 개인 맞춤형 **식단 분석·추천 리포트**를 생성하는 시스템.

**핵심 기능**

- 🥗 **식단 기록 관리** (음식/영양소 데이터 기반)
- ⏱ **영양 상태 추이 분석** (시계열 DB)
- 🧠 **AI 기반 맞춤 추천** (벡터 검색 + LLM)
- 📈 **리포트 시각화 및 자동화** (Grafana / Notion / PDF)

---

## 🏗️ 2. 전체 아키텍처 개요

```
[사용자 입력 / 이미지 식단 기록]
          │
          ▼
 ┌─────────────────────────┐
 │        🐬 MySQL          │
 │ 정형 데이터 저장 (User, Food, Meal) │
 │ - 사용자 정보 / 질병 / 음식 / 식단 │
 └─────────────────────────┘
          │
          ▼ (집계 / 배치)
 ┌─────────────────────────┐
 │     ⏱ TimescaleDB       │
 │ 시계열 데이터 (영양 추이) │
 │ - 일별/주별 영양 변화 기록 │
 └─────────────────────────┘
          │
          ▼ (LLM-friendly 요약)
 ┌─────────────────────────┐
 │       🧠 Vector DB       │
 │ (Qdrant / Redis Vector) │
 │ - 음식/유저 Embedding 저장 │
 │ - 유사도 기반 추천 검색 │
 └─────────────────────────┘
          │
          ▼
[Spring AI → 분석 & 추천 → Notion/PDF 리포트]
```

---

## 📘 3. DB별 역할 요약

| DB | 주요 역할 | 비고 |
| --- | --- | --- |
| **MySQL** | 사용자·식단·음식의 기본 데이터 관리 | 정형, 관계형 데이터 |
| **TimescaleDB** | 사용자의 시간 기반 영양 추이 관리 | PostgreSQL 확장 기반 |
| **Vector DB (Qdrant/Redis Vector)** | 음식·사용자 Embedding 벡터 저장 및 검색 | 의미 기반 유사도 탐색 |
| **Grafana / Notion / PDF** | 리포트 시각화 및 자동화 | AI 리포트 출력 목적 |

---

## 👤 4. 사용자 데이터 구조 (MySQL)

| 테이블 | 설명 | 주요 컬럼 |
| --- | --- | --- |
| `사용자` | 사용자 기본 정보 | id, name, age, sex, height, weight, goal |
| `사용자건강상태` | 질병/알러지 정보 | userId, condition |
| `음식` | 음식 및 영양소 데이터 | code, name, energy, protein, carb, fat |
| `식단` | 사용자의 식단 기록 | userId, type, createdAt |
| `식단음식` | 식단에 포함된 음식 | mealId, code, quantity |

**추가 뷰 (집계용)**

`v_daily_nutrition` — 사용자별 일별 총 섭취 영양소 합계

```sql
SELECT userId, DATE(createdAt) AS date,
SUM(energy), SUM(protein), SUM(carb), SUM(fat)
FROM 식단 JOIN 식단음식 JOIN 음식
GROUP BY userId, DATE(createdAt);
```

---

## ⏱ 5. 영양 상태 추이 데이터 (TimescaleDB)

| 테이블 | 설명 | 주요 컬럼 |
| --- | --- | --- |
| `nutrition_timeseries` | 사용자별 시간대별 영양 섭취량 및 체중 변화 기록 | user_id, ts, energy, protein, carb, fat, weight, blood_sugar |
| `daily_avg` (뷰) | 일별 평균값 (Continuous Aggregation) | user_id, day, avg_protein, avg_fat, avg_carb |

**특징**

- SQL 그대로 사용 가능 (`SELECT AVG(protein) FROM nutrition_timeseries ...`)
- `create_hypertable('nutrition_timeseries', 'ts')` 로 자동 파티셔닝
- Grafana 대시보드와 쉽게 연동 가능

---

## 🧠 6. 벡터 데이터 구조 (Vector DB)

### ✅ 음식 임베딩 (food_embedding)

| 필드 | 설명 |
| --- | --- |
| `food_code` | 음식 코드 |
| `name` | 음식 이름 |
| `category` | 한식/양식 등 |
| `vector` | Embedding (float[1536]) |
| `energy`, `protein`, `carb`, `fat` | 영양정보 (payload) |

**용도:**

→ 음식 이름/영양 조합을 Embedding화하여 유사 음식 추천에 활용

---

### ✅ 사용자 프로필 임베딩 (user_profile_embedding)

| 필드 | 설명 |
| --- | --- |
| `user_id` | 사용자 ID |
| `vector` | Embedding (float[1536]) |
| `payload` | age, sex, goal, conditions 등 |
| `trend_summary` | “단백질 증가, 지방 감소” 등 텍스트 요약 |

**생성 로직**

1. MySQL에서 사용자 정보 조회
2. TimescaleDB에서 최근 7일 추이 요약
3. 두 데이터를 결합해 자연어 문장화
4. OpenAI Embedding API → 벡터 변환 후 저장

예:

```
25세 여성, 감량 목표. 최근 7일 단백질 40g, 지방 25g, 체중 0.8kg 감소
```

---

## 📈 7. 데이터 흐름 요약 (ETL & AI Pipeline)

| 단계 | 입력 | 처리 | 출력 |
| --- | --- | --- | --- |
| ① | 식단 입력 (MySQL) | 음식-식단 조인으로 영양소 계산 | `v_daily_nutrition` |
| ② | 일별 집계 (Batch) | MySQL → TimescaleDB 이관 | `nutrition_timeseries` |
| ③ | 추이 요약 (Spring) | 최근 1주 데이터 SQL 집계 | “영양 상태 요약 텍스트” |
| ④ | 프로필 병합 | 사용자 정보 + 추이 문장 | Embedding 생성 |
| ⑤ | 벡터 저장 | Qdrant or Redis Vector | user_profile_embedding |
| ⑥ | 추천 검색 | user_embedding ↔ food_embedding | 유사도 Top-N 음식 추출 |
| ⑦ | AI 응답 | LLM Prompt 입력 (Spring AI) | 추천 메뉴 / 리포트 생성 |
| ⑧ | 결과 저장 | MySQL(리포트 테이블) + Notion/PDF | 사용자 제공 |

---

## 🧩 8. 예시 아키텍처 (시각적 요약)

```
                  ┌──────────────┐
                  │     MySQL     │
                  │ (User, Meal)  │
                  └───────┬──────┘
                          │
                 일별 집계/배치
                          ▼
                  ┌──────────────┐
                  │ TimescaleDB  │
                  │ (Trend Data) │
                  └───────┬──────┘
                          │
               Embedding 생성 (Spring AI)
                          ▼
                  ┌──────────────┐
                  │  Qdrant /    │
                  │ Redis Vector │
                  └───────┬──────┘
                          │
             유사 음식/유사 사용자 검색
                          ▼
                 ┌──────────────┐
                 │ Spring AI     │
                 │ (LLM 분석·추천)│
                 └───────┬──────┘
                          ▼
                PDF · Notion · Grafana
```

---

## 🧾 9. 개발 진행 흐름 (협업 참고용)

| 단계 | 담당 | 설명 |
| --- | --- | --- |
| **1. MySQL 모델링** | 백엔드 1 | 사용자/음식/식단 스키마 설계 |
| **2. TimescaleDB 연동** | 백엔드 2 | 일별 영양 집계 및 추이 저장 |
| **3. Qdrant/Redis Vector 구축** | AI 담당 | Embedding 생성 및 벡터 저장 로직 |
| **4. AI 분석 로직** | AI + 백엔드 | Spring AI Prompt 설계 및 응답 파싱 |
| **5. 리포트 생성/시각화** | 인프라/프론트 | Grafana·Notion 자동 업데이트 |

---

## ✅ 10. 핵심 정리

- **MySQL → 정적 데이터** (사용자, 식단, 음식)
- **TimescaleDB → 동적 데이터** (시간 기반 영양 추이)
- **Vector DB → 의미 데이터** (유사도 기반 추천)
- **AI Layer → 의사결정/리포트 생성**
- **Front Layer → 리포트 시각화**
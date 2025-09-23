package com.babsim.babsimbackend.domain.recommendation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 추천 요청 DTO
@Getter
@NoArgsConstructor
public class RecommendationRequest {
    // 현재는 요청 바디가 없지만, 추후 확장을 위해 파일을 생성합니다.
    // 예를 들어, 특정 카테고리나 재료를 포함/제외하는 필터링 조건이 추가될 수 있습니다.
    private String filter;
}

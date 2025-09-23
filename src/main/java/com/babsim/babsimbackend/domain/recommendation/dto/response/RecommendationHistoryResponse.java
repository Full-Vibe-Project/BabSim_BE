package com.babsim.babsimbackend.domain.recommendation.dto.response;

import com.babsim.babsimbackend.domain.recommendation.entity.RecommendationHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// AI 생성: 추천 이력 응답 DTO
@Getter
public class RecommendationHistoryResponse {
    private final Long menuId;
    private final String menuName;
    private final LocalDateTime recommendedAt;

    @Builder
    public RecommendationHistoryResponse(Long menuId, String menuName, LocalDateTime recommendedAt) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.recommendedAt = recommendedAt;
    }

    public static RecommendationHistoryResponse from(RecommendationHistory history) {
        return RecommendationHistoryResponse.builder()
                .menuId(history.getMenuId())
                .menuName(history.getMenuName())
                .recommendedAt(history.getCreatedAt())
                .build();
    }
}

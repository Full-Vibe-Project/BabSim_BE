package com.babsim.babsimbackend.domain.diet.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "음식 사진 분석 요청 DTO")
public record FoodAnalyzeRequest(
    @NotBlank
    @Schema(description = "분석할 이미지 URL")
    String imageUrl
) {
}

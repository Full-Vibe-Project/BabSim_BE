package com.babsim.babsimbackend.domain.diet.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "음식 사진 업로드 응답 DTO")
public record FoodImageUploadResponse(
    @Schema(description = "업로드된 이미지 URL")
    String imageUrl
) {
}

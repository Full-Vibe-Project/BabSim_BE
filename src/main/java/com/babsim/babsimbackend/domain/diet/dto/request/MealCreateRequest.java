package com.babsim.babsimbackend.domain.diet.dto.request;

import com.babsim.babsimbackend.domain.diet.enums.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "식단 생성 요청 DTO")
public record MealCreateRequest(
    @NotNull
    @Schema(description = "식사 유형 (BREAKFAST, LUNCH, DINNER, SNACK)")
    MealType mealType,

    @Schema(description = "식단 이미지 URL")
    String imageUrl,

    @NotEmpty
    @Schema(description = "식단을 구성하는 음식 목록")
    List<SelectedFood> foods
) {
    @Schema(description = "사용자가 선택한 음식 정보")
    public record SelectedFood(
        @Schema(description = "음식 코드")
        String foodCode,

        @Schema(description = "수량")
        Integer quantity
    ) {
    }
}

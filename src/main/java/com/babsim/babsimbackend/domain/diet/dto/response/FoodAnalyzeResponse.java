package com.babsim.babsimbackend.domain.diet.dto.response;

import com.babsim.babsimbackend.domain.diet.entity.Food;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "음식 사진 분석 결과 응답 DTO")
public record FoodAnalyzeResponse(
    List<AnalyzedFood> foods
) {
    @Schema(description = "분석된 개별 음식 정보")
    public record AnalyzedFood(
        @Schema(description = "AI가 인식한 음식 이름")
        String recognizedName,

        @Schema(description = "DB에서 매칭된 음식 정보")
        Food matchedFood
    ) {
    }
}

//package com.babsim.babsimbackend.domain.recommendation.dto.response;
//
//import com.babsim.babsimbackend.domain.diet.dto.response.NutrientResponse;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.List;
//
//// AI 생성: 메뉴 추천 응답 DTO
//@Getter
//public class MenuRecommendationResponse {
//    private final List<RecommendedMenu> recommendations;
//
//    @Builder
//    public MenuRecommendationResponse(List<RecommendedMenu> recommendations) {
//        this.recommendations = recommendations;
//    }
//
//    @Getter
//    public static class RecommendedMenu {
//        private final String name;
//        private final String description;
//        private final NutrientResponse nutrients;
//
//        @Builder
//        public RecommendedMenu(String name, String description, NutrientResponse nutrients) {
//            this.name = name;
//            this.description = description;
//            this.nutrients = nutrients;
//        }
//    }
//}

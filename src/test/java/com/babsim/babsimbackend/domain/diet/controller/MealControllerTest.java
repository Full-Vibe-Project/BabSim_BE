package com.babsim.babsimbackend.domain.diet.controller;

import com.babsim.babsimbackend.domain.diet.dto.request.MealCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.MealUpdateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.MealResponse;
import com.babsim.babsimbackend.domain.diet.entity.Meal;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import com.babsim.babsimbackend.domain.diet.service.MealService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// AI 생성: MealController의 API 엔드포인트를 검증하기 위한 단위 테스트
@WebMvcTest(controllers = MealController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MealService mealService;

    @Test
    @DisplayName("식단 생성을 요청하면 201 Created 상태와 Location 헤더를 반환한다.")
    void givenMealCreateRequest_whenPostMeal_thenReturns201AndLocationHeader() throws Exception {
        // given
        UUID userId = UUID.randomUUID();
        MealCreateRequest.FoodItem foodItem = new MealCreateRequest.FoodItem("D000001", 1);
        MealCreateRequest request = new MealCreateRequest(userId, MealType.LUNCH, "http://image.com/lunch.jpg", List.of(foodItem));

        MealResponse responseDto = new MealResponse(1L, MealType.LUNCH, "http://image.com/lunch.jpg", LocalDateTime.now(), Collections.emptyList());

        given(mealService.createMeal(any(MealCreateRequest.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/v1/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
    }

    @Test
    @DisplayName("사용자 ID로 식단 목록 조회를 요청하면 200 OK 상태와 함께 식단 목록을 반환한다.")
    void givenUserId_whenGetMeals_thenReturns200AndMealList() throws Exception {
        // given
        UUID userId = UUID.randomUUID();
        given(mealService.findMealsByUserId(userId)).willReturn(Collections.emptyList());

        // when & then
        mockMvc.perform(get("/api/v1/meals/user/{userId}", userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("식단 수정을 요청하면 200 OK 상태와 함께 수정된 식단 정보를 반환한다.")
    void givenMealUpdateRequest_whenPutMeal_thenReturns200AndUpdatedMeal() throws Exception {
        // given
        Long mealId = 1L;
        MealUpdateRequest.FoodItem foodItem = new MealUpdateRequest.FoodItem("D000002", 1);
        MealUpdateRequest request = new MealUpdateRequest(MealType.DINNER, null, List.of(foodItem));

        MealResponse responseDto = new MealResponse(mealId, MealType.DINNER, null, LocalDateTime.now(), Collections.emptyList());

        given(mealService.updateMeal(eq(mealId), any(MealUpdateRequest.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/v1/meals/{mealId}", mealId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.mealType").value("DINNER"));
    }

    @Test
    @DisplayName("식단 삭제를 요청하면 204 No Content 상태를 반환한다.")
    void givenMealId_whenDeleteMeal_thenReturns204NoContent() throws Exception {
        // given
        Long mealId = 1L;

        // when & then
        mockMvc.perform(delete("/api/v1/meals/{mealId}", mealId))
            .andExpect(status().isNoContent());

        verify(mealService).deleteMeal(mealId);
    }
}

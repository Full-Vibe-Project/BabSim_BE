package com.babsim.babsimbackend.domain.diet.controller;

import com.babsim.babsimbackend.domain.diet.dto.request.FoodAnalyzeRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.FoodCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.FoodUpdateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodAnalyzeResponse;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodImageUploadResponse;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodResponse;
import com.babsim.babsimbackend.domain.diet.service.FoodService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// AI 생성: FoodController의 API 엔드포인트를 검증하기 위한 단위 테스트
@WebMvcTest(controllers = FoodController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FoodService foodService;

    @Test
    @DisplayName("음식 사진을 업로드하면 200 OK와 함께 이미지 URL을 반환한다.")
    void givenFoodImage_whenUploadFoodImage_thenReturns200AndImageUrl() throws Exception {
        // given
        MockMultipartFile mockFile = new MockMultipartFile("foodImage", "test.jpg", "image/jpeg", "test image".getBytes());
        FoodImageUploadResponse response = new FoodImageUploadResponse("s3-url");
        given(foodService.uploadFoodImage(any())).willReturn(response);

        // when & then
        mockMvc.perform(multipart("/api/v1/foods/image")
                .file(mockFile))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.imageUrl").value("s3-url"));
    }

    @Test
    @DisplayName("이미지 URL로 음식 분석을 요청하면 200 OK와 함께 분석 결과를 반환한다.")
    void givenImageUrl_whenAnalyzeFoodImage_thenReturns200AndAnalysisResult() throws Exception {
        // given
        FoodAnalyzeRequest request = new FoodAnalyzeRequest("s3-url");
        FoodAnalyzeResponse response = new FoodAnalyzeResponse(Collections.emptyList());
        given(foodService.analyzeFoodImage(any())).willReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/foods/analyze")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("음식 생성을 요청하면 201 Created 상태와 함께 생성된 음식 정보를 반환한다.")
    void givenFoodCreateRequest_whenPostFood_thenReturns201AndFoodResponse() throws Exception {
        // given
        FoodCreateRequest requestDto = new FoodCreateRequest("D000001", "쌀밥", 313.0, 69.9, 6.6, 0.9, "210g");
        FoodResponse responseDto = new FoodResponse(requestDto.toEntity());
        given(foodService.createFood(any(FoodCreateRequest.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/v1/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("음식 코드로 조회를 요청하면 200 OK 상태와 함께 해당 음식 정보를 반환한다.")
    void givenFoodCode_whenGetFood_thenReturns200AndFoodResponse() throws Exception {
        // given
        String foodCode = "D000001";
        FoodResponse responseDto = new FoodResponse(foodCode, "쌀밥", 313.0, 69.9, 6.6, 0.9, "210g");
        given(foodService.findFoodByCode(foodCode)).willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/v1/foods/{code}", foodCode))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(foodCode));
    }

    @Test
    @DisplayName("음식 수정을 요청하면 200 OK 상태와 함께 수정된 음식 정보를 반환한다.")
    void givenFoodUpdateRequest_whenPutFood_thenReturns200AndUpdatedFoodResponse() throws Exception {
        // given
        String foodCode = "D000001";
        FoodUpdateRequest requestDto = new FoodUpdateRequest("현미밥", 321.0, null, null, null, null);
        FoodResponse responseDto = new FoodResponse(foodCode, "현미밥", 321.0, 69.9, 6.6, 0.9, "210g");
        given(foodService.updateFood(eq(foodCode), any(FoodUpdateRequest.class))).willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/v1/foods/{code}", foodCode)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("음식 삭제를 요청하면 204 No Content 상태를 반환한다.")
    void givenFoodCode_whenDeleteFood_thenReturns204NoContent() throws Exception {
        // given
        String foodCode = "D000001";

        // when & then
        mockMvc.perform(delete("/api/v1/foods/{code}", foodCode))
            .andExpect(status().isNoContent());

        verify(foodService).deleteFood(foodCode);
    }
}

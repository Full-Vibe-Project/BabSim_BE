package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.diet.dto.request.FoodCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodResponse;
import com.babsim.babsimbackend.domain.diet.dto.request.FoodUpdateRequest;
import com.babsim.babsimbackend.domain.diet.entity.Food;
import com.babsim.babsimbackend.domain.diet.exception.FoodNotFoundException;
import com.babsim.babsimbackend.domain.diet.repository.FoodRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// AI 생성: FoodService의 비즈니스 로직을 검증하기 위한 단위 테스트
@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @InjectMocks
    private FoodService foodService;

    @Mock
    private FoodRepository foodRepository;

    @Test
    @DisplayName("새로운 음식 정보를 요청하면 성공적으로 생성하고 결과를 반환해야 한다.")
    void givenFoodCreateRequest_whenCreateFood_thenFoodIsSavedAndReturned() {
        // given
        FoodCreateRequest requestDto = new FoodCreateRequest("D000002", "김치찌개", 120.0, 5.0, 8.0, 7.0, "400g");
        Food savedFood = Food.builder().code("D000002").name("김치찌개").build();
        when(foodRepository.save(any(Food.class))).thenReturn(savedFood);

        // when
        FoodResponse responseDto = foodService.createFood(requestDto);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.code()).isEqualTo("D000002");
    }

    @Test
    @DisplayName("존재하는 음식 코드로 조회하면 해당 음식 정보를 반환해야 한다.")
    void givenExistingFoodCode_whenFindFoodByCode_thenReturnsFoodResponse() {
        // given
        String foodCode = "D000001";
        Food foundFood = Food.builder().code(foodCode).name("쌀밥").build();
        when(foodRepository.findById(foodCode)).thenReturn(Optional.of(foundFood));

        // when
        FoodResponse responseDto = foodService.findFoodByCode(foodCode);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.code()).isEqualTo(foodCode);
    }

    @Test
    @DisplayName("존재하지 않는 음식 코드로 조회하면 FoodNotFoundException 예외가 발생해야 한다.")
    void givenNonExistingFoodCode_whenFindFoodByCode_thenThrowsFoodNotFoundException() {
        // given
        String foodCode = "D999999";
        when(foodRepository.findById(foodCode)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> foodService.findFoodByCode(foodCode))
            .isInstanceOf(FoodNotFoundException.class);
    }

    @Test
    @DisplayName("존재하는 음식 정보를 수정 요청하면 성공적으로 수정하고 결과를 반환해야 한다.")
    void givenExistingFoodCodeAndUpdateRequest_whenUpdateFood_thenFoodIsUpdated() {
        // given
        String foodCode = "D000001";
        FoodUpdateRequest updateRequest = new FoodUpdateRequest("현미밥", 321.0, null, null, null, null);
        Food existingFood = Food.builder().code(foodCode).name("쌀밥").energy(313.0).build();
        when(foodRepository.findById(foodCode)).thenReturn(Optional.of(existingFood));

        // when
        FoodResponse updatedResponse = foodService.updateFood(foodCode, updateRequest);

        // then
        assertThat(updatedResponse).isNotNull();
        assertThat(updatedResponse.name()).isEqualTo("현미밥");
        assertThat(updatedResponse.energy()).isEqualTo(321.0);
    }

    @Test
    @DisplayName("존재하는 음식 코드로 삭제를 요청하면 해당 음식이 삭제되어야 한다.")
    void givenExistingFoodCode_whenDeleteFood_thenFoodIsDeleted() {
        // given
        String foodCode = "D000001";

        // when
        foodService.deleteFood(foodCode);

        // then
        verify(foodRepository).deleteById(foodCode);
    }
}

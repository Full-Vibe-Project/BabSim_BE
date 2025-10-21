package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import com.babsim.babsimbackend.domain.diet.dto.request.MealCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.MealUpdateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.MealResponse;
import com.babsim.babsimbackend.domain.diet.entity.Food;
import com.babsim.babsimbackend.domain.diet.entity.Meal;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import com.babsim.babsimbackend.domain.diet.exception.MealNotFoundException;
import com.babsim.babsimbackend.domain.diet.repository.FoodRepository;
import com.babsim.babsimbackend.domain.diet.repository.MealRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// AI 생성: MealService의 비즈니스 로직을 검증하기 위한 단위 테스트
@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @InjectMocks
    private MealService mealService;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FoodRepository foodRepository;

    @Test
    @DisplayName("새로운 식단 정보를 요청하면 성공적으로 생성하고 결과를 반환해야 한다.")
    void givenMealCreateRequest_whenCreateMeal_thenMealIsSavedAndReturned() {
        // given
        UUID userId = UUID.randomUUID();
        User user = User.builder().name("testUser").build();
        Food food1 = Food.builder().code("D000001").name("쌀밥").build();
        MealCreateRequest.FoodItem foodItemRequest = new MealCreateRequest.FoodItem("D000001", 1);
        MealCreateRequest request = new MealCreateRequest(userId, MealType.LUNCH, "http://image.com/lunch.jpg", List.of(foodItemRequest));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(foodRepository.findById("D000001")).thenReturn(Optional.of(food1));
        when(mealRepository.save(any(Meal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        MealResponse response = mealService.createMeal(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.mealType()).isEqualTo(MealType.LUNCH);
        assertThat(response.foods()).hasSize(1);
        assertThat(response.foods().get(0).foodName()).isEqualTo("쌀밥");
    }

    @Test
    @DisplayName("사용자 ID로 식단 목록 조회를 요청하면 해당 사용자의 모든 식단 정보를 반환해야 한다.")
    void givenUserId_whenFindMeals_thenReturnsMealList() {
        // given
        UUID userId = UUID.randomUUID();
        User user = User.builder().name("testUser").build();
        Meal meal1 = Meal.builder().user(user).mealType(MealType.BREAKFAST).build();
        Meal meal2 = Meal.builder().user(user).mealType(MealType.LUNCH).build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mealRepository.findAllByUser(user)).thenReturn(List.of(meal1, meal2));

        // when
        List<MealResponse> responses = mealService.findMealsByUserId(userId);

        // then
        assertThat(responses).isNotNull();
        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("식단 정보 수정을 요청하면 성공적으로 수정하고 결과를 반환해야 한다.")
    void givenMealUpdateRequest_whenUpdateMeal_thenMealIsUpdated() {
        // given
        Long mealId = 1L;
        User user = User.builder().name("testUser").build();
        Meal existingMeal = Meal.builder().user(user).mealType(MealType.LUNCH).build();
        Food newFood = Food.builder().code("D000002").name("김치찌개").build();

        MealUpdateRequest.FoodItem updatedFoodItem = new MealUpdateRequest.FoodItem("D000002", 1);
        MealUpdateRequest request = new MealUpdateRequest(MealType.DINNER, null, List.of(updatedFoodItem));

        when(mealRepository.findById(mealId)).thenReturn(Optional.of(existingMeal));
        when(foodRepository.findById("D000002")).thenReturn(Optional.of(newFood));

        // when
        MealResponse response = mealService.updateMeal(mealId, request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.mealType()).isEqualTo(MealType.DINNER);
        assertThat(response.foods()).hasSize(1);
        assertThat(response.foods().get(0).foodName()).isEqualTo("김치찌개");
    }

    @Test
    @DisplayName("존재하는 식단 ID로 삭제를 요청하면 해당 식단이 삭제되어야 한다.")
    void givenExistingMealId_whenDeleteMeal_thenMealIsDeleted() {
        // given
        Long mealId = 1L;
        when(mealRepository.existsById(mealId)).thenReturn(true);

        // when
        mealService.deleteMeal(mealId);

        // then
        verify(mealRepository).deleteById(mealId);
    }

    @Test
    @DisplayName("존재하지 않는 식단 ID로 삭제를 요청하면 MealNotFoundException 예외가 발생해야 한다.")
    void givenNonExistingMealId_whenDeleteMeal_thenThrowsMealNotFoundException() {
        // given
        Long mealId = 99L;
        when(mealRepository.existsById(mealId)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> mealService.deleteMeal(mealId))
            .isInstanceOf(MealNotFoundException.class);
    }
}

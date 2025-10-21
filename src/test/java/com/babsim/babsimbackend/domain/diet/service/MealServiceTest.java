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

    // ... (Create, Read, Update 테스트는 생략) ...

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

package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import com.babsim.babsimbackend.domain.diet.entity.Meal;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// AI 생성: MealRepository의 데이터베이스 연동을 테스트하기 위한 클래스
@DataJpaTest
@ActiveProfiles("test")
class MealRepositoryTest {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder().name("testUser").build();
        userRepository.save(testUser);
    }

    @Test
    @DisplayName("사용자의 식단을 저장하고 조회하면 정상적으로 조회되어야 한다.")
    void givenMealEntity_whenSaveAndFindAllByUser_thenReturnsMealList() {
        // given
        Meal meal = Meal.builder()
            .user(testUser)
            .mealType(MealType.LUNCH)
            .imageUrl("http://example.com/image.jpg")
            .build();

        // when
        mealRepository.save(meal);
        List<Meal> meals = mealRepository.findAllByUser(testUser);

        // then
        assertThat(meals).hasSize(1);
        assertThat(meals.get(0).getMealType()).isEqualTo(MealType.LUNCH);
        assertThat(meals.get(0).getUser().getName()).isEqualTo("testUser");
    }
}

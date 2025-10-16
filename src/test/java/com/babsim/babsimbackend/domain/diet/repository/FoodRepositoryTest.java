package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.diet.entity.Food;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

// AI 생성: FoodRepository의 데이터베이스 연동을 테스트하기 위한 클래스
@DataJpaTest
@ActiveProfiles("test") // "test" 프로필을 활성화하여 application-test.yml 설정을 사용합니다.
class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    @DisplayName("음식 정보를 저장하고 코드로 조회하면 정상적으로 조회되어야 한다.")
    void givenFoodEntity_whenSaveAndFindById_thenIsPersisted() {
        // given
        Food newFood = Food.builder()
            .code("D000001")
            .name("쌀밥")
            .energy(313.0)
            .carb(69.9)
            .protein(6.6)
            .fat(0.9)
            .weight("210g")
            .build();

        // when
        foodRepository.save(newFood);
        Food foundFood = foodRepository.findById(newFood.getCode()).orElse(null);

        // then
        assertThat(foundFood).isNotNull();
        assertThat(foundFood.getCode()).isEqualTo("D000001");
        assertThat(foundFood.getName()).isEqualTo("쌀밥");
    }
}

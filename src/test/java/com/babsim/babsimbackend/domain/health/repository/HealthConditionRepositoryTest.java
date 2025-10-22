package com.babsim.babsimbackend.domain.health.repository;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class HealthConditionRepositoryTest {

    @Autowired
    private HealthConditionRepository healthConditionRepository;

    @DisplayName("given_HealthCondition_when_save_then_success")
    @Test
    void given_HealthCondition_when_save_then_success() {
        // given
        HealthCondition healthCondition = HealthCondition.builder()
                .name("고혈압")
                .type(HealthConditionType.CHRONIC_DISEASE)
                .build();

        // when
        HealthCondition savedHealthCondition = healthConditionRepository.save(healthCondition);

        // then
        assertThat(savedHealthCondition.getId()).isNotNull();
        assertThat(savedHealthCondition.getName()).isEqualTo("고혈압");
        assertThat(savedHealthCondition.getType()).isEqualTo(HealthConditionType.CHRONIC_DISEASE);
    }
}

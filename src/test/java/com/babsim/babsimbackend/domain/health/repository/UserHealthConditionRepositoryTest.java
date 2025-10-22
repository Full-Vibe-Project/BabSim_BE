package com.babsim.babsimbackend.domain.health.repository;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.enums.Gender;
import com.babsim.babsimbackend.domain.user.enums.GoalType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserHealthConditionRepositoryTest {

    @Autowired
    private UserHealthConditionRepository userHealthConditionRepository;

    @Autowired
    private HealthConditionRepository healthConditionRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    // Assuming UserRepository exists and can be mocked or used for setup
    // For simplicity, we'll create a dummy User here.
    private User createUser(UUID id, String name) {
        User user = User.builder()
                .name(name)
                .gender(Gender.MALE)
                .age(30)
                .height(new java.math.BigDecimal("170.0"))
                .weight(new java.math.BigDecimal("60.0"))
                .goal(com.babsim.babsimbackend.domain.user.enums.GoalType.WEIGHT_LOSS)
                .build();
        ReflectionTestUtils.setField(user, "id", id);
        // Persist the user before returning
        return testEntityManager.persistAndFlush(user);
    }

    private HealthCondition createHealthCondition(Long id, String name, HealthConditionType type) {
        HealthCondition healthCondition = HealthCondition.builder()
                .name(name)
                .type(type)
                .build();
        ReflectionTestUtils.setField(healthCondition, "id", id);
        // Persist the health condition before returning
        return testEntityManager.persistAndFlush(healthCondition);
    }

    @DisplayName("given_UserHealthCondition_when_save_then_success")
    @Test
    void given_UserHealthCondition_when_save_then_success() {
        // given
        User user = createUser(UUID.randomUUID(), "testUser");
        HealthCondition healthCondition = createHealthCondition(1L, "고혈압", HealthConditionType.CHRONIC_DISEASE);

        UserHealthCondition userHealthCondition = UserHealthCondition.builder()
                .user(user)
                .healthCondition(healthCondition)
                .build();

        // when
        UserHealthCondition savedUserHealthCondition = userHealthConditionRepository.save(userHealthCondition);

        // then
        assertThat(savedUserHealthCondition.getId()).isNotNull();
        assertThat(savedUserHealthCondition.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedUserHealthCondition.getHealthCondition().getId()).isEqualTo(healthCondition.getId());
    }
}

package com.babsim.babsimbackend.domain.health.repository;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@EntityScan(basePackages = {
	"com.babsim.babsimbackend.domain"
})
@DataJpaTest
@ActiveProfiles("test")
class UserHealthConditionRepositoryTest {

	@Autowired
	private UserHealthConditionRepository userHealthConditionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HealthConditionRepository healthConditionRepository;

	private User user;
	private HealthCondition healthCondition;

	@BeforeEach
	void setUp() {
		user = User.builder()
			.name("testUser")
			.build();
		user = userRepository.save(user);

		healthCondition = HealthCondition.builder()
			.name("당뇨")
			.type(HealthConditionType.DIABETES)
			.build();
		healthCondition = healthConditionRepository.save(healthCondition);
	}

	@Test
	@DisplayName("사용자 건강 상태 저장 및 조회")
	void saveAndFindById_success() {
		// Given
		UserHealthCondition userHealthCondition = UserHealthCondition.builder()
			.user(user)
			.healthCondition(healthCondition)
			.build();

		// When
		UserHealthCondition savedMapping = userHealthConditionRepository.save(userHealthCondition);
		Optional<UserHealthCondition> foundMapping = userHealthConditionRepository.findById(savedMapping.getId());

		// Then
		assertThat(foundMapping).isPresent();
		assertThat(foundMapping.get().getId()).isEqualTo(savedMapping.getId());
		assertThat(foundMapping.get().getUser()).isEqualTo(user);
		assertThat(foundMapping.get().getHealthCondition()).isEqualTo(healthCondition);
	}
}
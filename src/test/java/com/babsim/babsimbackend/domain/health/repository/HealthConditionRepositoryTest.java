package com.babsim.babsimbackend.domain.health.repository;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@EntityScan(basePackages = {
	"com.babsim.babsimbackend.domain",
	"com.babsim.babsimbackend.common.entity"
})
@ActiveProfiles("test")
@DataJpaTest
class HealthConditionRepositoryTest {

	@Autowired
	private HealthConditionRepository healthConditionRepository;

	@Test
	@DisplayName("건강 상태 저장 및 ID로 조회")
	void saveAndFindById_success() {
		// Given
		HealthCondition healthCondition = HealthCondition.builder()
			.name("당뇨")
			.type(HealthConditionType.DIABETES)
			.build();

		// When
		HealthCondition savedCondition = healthConditionRepository.save(healthCondition);
		Optional<HealthCondition> foundCondition = healthConditionRepository.findById(savedCondition.getId());

		// Then
		assertThat(foundCondition).isPresent();
		assertThat(foundCondition.get().getId()).isEqualTo(savedCondition.getId());
		assertThat(foundCondition.get().getName()).isEqualTo("당뇨");
	}

	@Test
	@DisplayName("모든 건강 상태 조회")
	void findAll_success() {
		// Given
		HealthCondition condition1 = HealthCondition.builder().name("당뇨").type(HealthConditionType.DIABETES).build();
		HealthCondition condition2 = HealthCondition.builder().name("고혈압").type(HealthConditionType.HYPERTENSION).build();
		healthConditionRepository.save(condition1);
		healthConditionRepository.save(condition2);

		// When
		List<HealthCondition> conditions = healthConditionRepository.findAll();

		// Then
		assertThat(conditions).isNotNull();
		assertThat(conditions.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("건강 상태 삭제")
	void delete_success() {
		// Given
		HealthCondition healthCondition = HealthCondition.builder().name("당뇨").type(HealthConditionType.DIABETES).build();
		HealthCondition savedCondition = healthConditionRepository.save(healthCondition);
		Long id = savedCondition.getId();

		assertThat(healthConditionRepository.findById(id)).isPresent();

		// When
		healthConditionRepository.deleteById(id);
		Optional<HealthCondition> foundCondition = healthConditionRepository.findById(id);

		// Then
		assertThat(foundCondition).isNotPresent();
	}
}
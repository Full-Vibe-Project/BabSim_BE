package com.babsim.babsimbackend.domain.health.service;

import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.health.exception.HealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.repository.HealthConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class HealthConditionServiceTest {

	@InjectMocks
	private HealthConditionService healthConditionService;

	@Mock
	private HealthConditionRepository healthConditionRepository;

	private HealthCondition healthCondition;
	private Long healthConditionId;

	@BeforeEach
	void setUp() {
		healthConditionId = 1L;
		healthCondition = HealthCondition.builder()
			.name("당뇨")
			.type(HealthConditionType.DIABETES)
			.build();

	}

	@Test
	@DisplayName("건강 상태 생성 성공")
	void createHealthCondition_success() {
		// Given
		HealthConditionCreateRequest request = new HealthConditionCreateRequest("당뇨", HealthConditionType.DIABETES);

		given(healthConditionRepository.save(any(HealthCondition.class))).willReturn(healthCondition);

		// When
		HealthCondition result = healthConditionService.createHealthCondition(request);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("당뇨");
		assertThat(result.getType()).isEqualTo(HealthConditionType.DIABETES);

		then(healthConditionRepository).should(times(1)).save(any(HealthCondition.class));
	}

	@Test
	@DisplayName("ID로 건강 상태 조회 성공")
	void getHealthConditionById_success() {
		// Given
		given(healthConditionRepository.findById(healthConditionId)).willReturn(Optional.of(healthCondition));

		// When
		HealthCondition result = healthConditionService.getHealthConditionById(healthConditionId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(healthCondition);
		then(healthConditionRepository).should(times(1)).findById(healthConditionId);
	}

	@Test
	@DisplayName("ID로 건강 상태 조회 실패 - 찾을 수 없음")
	void getHealthConditionById_fail_notFound() {
		// Given
		Long notFoundId = 999L;
		given(healthConditionRepository.findById(notFoundId)).willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> healthConditionService.getHealthConditionById(notFoundId))
			.isInstanceOf(HealthConditionNotFoundException.class);

		then(healthConditionRepository).should(times(1)).findById(notFoundId);
	}

	@Test
	@DisplayName("모든 건강 상태 목록 조회 성공")
	void getAllHealthConditions_success() {
		// Given
		HealthCondition healthCondition2 = HealthCondition.builder().name("고혈압").type(HealthConditionType.HYPERTENSION).build();
		List<HealthCondition> list = List.of(healthCondition, healthCondition2);

		given(healthConditionRepository.findAll()).willReturn(list);

		// When
		List<HealthCondition> result = healthConditionService.getAllHealthConditions();

		// Then
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(2);
		assertThat(result).contains(healthCondition, healthCondition2);
		then(healthConditionRepository).should(times(1)).findAll();
	}

	@Test
	@DisplayName("건강 상태 수정 성공")
	void updateHealthCondition_success() {
		// Given
		HealthConditionUpdateRequest request = new HealthConditionUpdateRequest("비만", HealthConditionType.OBESITY);

		given(healthConditionRepository.findById(healthConditionId)).willReturn(Optional.of(healthCondition));
		given(healthConditionRepository.save(any(HealthCondition.class))).willReturn(healthCondition);


		// When
		HealthCondition result = healthConditionService.updateHealthCondition(healthConditionId, request);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("비만"); // update 메서드에 의해 변경된 값 확인
		assertThat(result.getType()).isEqualTo(HealthConditionType.OBESITY);

		then(healthConditionRepository).should(times(1)).findById(healthConditionId);
		then(healthConditionRepository).should(times(1)).save(healthCondition); // save 호출 검증
	}

	@Test
	@DisplayName("건강 상태 수정 실패 - 찾을 수 없음")
	void updateHealthCondition_fail_notFound() {
		// Given
		Long notFoundId = 999L;
		HealthConditionUpdateRequest request = new HealthConditionUpdateRequest("비만", HealthConditionType.OBESITY);

		given(healthConditionRepository.findById(notFoundId)).willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> healthConditionService.updateHealthCondition(notFoundId, request))
			.isInstanceOf(HealthConditionNotFoundException.class);

		then(healthConditionRepository).should(times(1)).findById(notFoundId);
		then(healthConditionRepository).should(never()).save(any(HealthCondition.class)); // save가 호출되지 않았는지 검증
	}

	@Test
	@DisplayName("건강 상태 삭제 성공")
	void deleteHealthCondition_success() {
		// Given

		// When
		healthConditionService.deleteHealthCondition(healthConditionId);

		// Then
		then(healthConditionRepository).should(times(1)).deleteById(healthConditionId);
	}
}
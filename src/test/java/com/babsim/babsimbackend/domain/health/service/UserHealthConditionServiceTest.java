package com.babsim.babsimbackend.domain.health.service;

import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.health.exception.HealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.exception.UserHealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.repository.HealthConditionRepository;
import com.babsim.babsimbackend.domain.health.repository.UserHealthConditionRepository;
import com.babsim.babsimbackend.domain.user.entity.User; // User 엔티티 import
import com.babsim.babsimbackend.domain.user.exception.UserNotFoundException; // User 예외 import
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHealthConditionServiceTest {

	@InjectMocks
	private UserHealthConditionService userHealthConditionService;

	@Mock
	private UserHealthConditionRepository userHealthConditionRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private HealthConditionRepository healthConditionRepository;

	private User user;
	private HealthCondition healthCondition;
	private UserHealthCondition userHealthCondition;

	private UUID userId;
	private Long healthConditionId;
	private Long userHealthConditionId;

	@BeforeEach
	void setUp() {
		user = mock(User.class);
		healthCondition = HealthCondition.builder()
			.name("당뇨")
			.type(HealthConditionType.DIABETES)
			.build();


		userId = UUID.randomUUID();
		healthConditionId = 1L;
		userHealthConditionId = 10L;



		userHealthCondition = UserHealthCondition.builder()
			.user(user)
			.healthCondition(healthCondition)
			.build();
	}

	@Test
	@DisplayName("사용자 건강 상태 생성 성공")
	void createUserHealthCondition_success() {
		// Given
		UserHealthConditionCreateRequest request = new UserHealthConditionCreateRequest(userId, healthConditionId);


		given(userRepository.findById(userId)).willReturn(Optional.of(user));
		given(healthConditionRepository.findById(healthConditionId)).willReturn(Optional.of(healthCondition));
		given(userHealthConditionRepository.save(any(UserHealthCondition.class))).willReturn(userHealthCondition);

		// When
		UserHealthCondition result = userHealthConditionService.createUserHealthCondition(request);

		// Then
		assertThat(result).isNotNull();
		assertThat(result.getUser()).isEqualTo(user);
		assertThat(result.getHealthCondition()).isEqualTo(healthCondition);

		then(userRepository).should(times(1)).findById(userId);
		then(healthConditionRepository).should(times(1)).findById(healthConditionId);
		then(userHealthConditionRepository).should(times(1)).save(any(UserHealthCondition.class));
	}

	@Test
	@DisplayName("사용자 건강 상태 생성 실패 - 사용자를 찾을 수 없음")
	void createUserHealthCondition_fail_userNotFound() {
		// Given
		UserHealthConditionCreateRequest request = new UserHealthConditionCreateRequest(userId, healthConditionId);


		given(userRepository.findById(userId)).willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> userHealthConditionService.createUserHealthCondition(request))
			.isInstanceOf(UserNotFoundException.class); // UserNotFoundException을 가정합니다.

		then(userRepository).should(times(1)).findById(userId);
		then(healthConditionRepository).should(never()).findById(anyLong());
		then(userHealthConditionRepository).should(never()).save(any(UserHealthCondition.class));
	}

	@Test
	@DisplayName("사용자 건강 상태 생성 실패 - 건강 상태를 찾을 수 없음")
	void createUserHealthCondition_fail_healthConditionNotFound() {
		// Given
		UserHealthConditionCreateRequest request = new UserHealthConditionCreateRequest(userId, healthConditionId);

		given(userRepository.findById(userId)).willReturn(Optional.of(user));
		given(healthConditionRepository.findById(healthConditionId)).willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> userHealthConditionService.createUserHealthCondition(request))
			.isInstanceOf(HealthConditionNotFoundException.class);

		then(userRepository).should(times(1)).findById(userId);
		then(healthConditionRepository).should(times(1)).findById(healthConditionId);
		then(userHealthConditionRepository).should(never()).save(any(UserHealthCondition.class));
	}

	@Test
	@DisplayName("ID로 사용자 건강 상태 조회 성공")
	void getUserHealthConditionById_success() {
		// Given
		given(userHealthConditionRepository.findById(userHealthConditionId)).willReturn(Optional.of(userHealthCondition));

		// When
		UserHealthCondition result = userHealthConditionService.getUserHealthConditionById(userHealthConditionId);

		// Then
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(userHealthCondition);
		then(userHealthConditionRepository).should(times(1)).findById(userHealthConditionId);
	}

	@Test
	@DisplayName("ID로 사용자 건강 상태 조회 실패 - 찾을 수 없음")
	void getUserHealthConditionById_fail_notFound() {
		// Given
		Long notFoundId = 999L;
		given(userHealthConditionRepository.findById(notFoundId)).willReturn(Optional.empty());

		// When & Then
		assertThatThrownBy(() -> userHealthConditionService.getUserHealthConditionById(notFoundId))
			.isInstanceOf(UserHealthConditionNotFoundException.class);

		then(userHealthConditionRepository).should(times(1)).findById(notFoundId);
	}

	@Test
	@DisplayName("모든 사용자 건강 상태 목록 조회 성공")
	void getAllUserHealthConditions_success() {
		// Given
		List<UserHealthCondition> list = List.of(userHealthCondition);
		given(userHealthConditionRepository.findAll()).willReturn(list);

		// When
		List<UserHealthCondition> result = userHealthConditionService.getAllUserHealthConditions();

		// Then
		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		then(userHealthConditionRepository).should(times(1)).findAll();
	}

	@Test
	@DisplayName("사용자 건강 상태 수정 성공")
	void updateUserHealthCondition_success() {
		// Given
		User newUser = mock(User.class);
		UUID newUserId = UUID.randomUUID();
		HealthCondition newHealthCondition = HealthCondition.builder().name("고혈압").type(HealthConditionType.HYPERTENSION).build();
		Long newHealthConditionId = 2L;

		UserHealthConditionUpdateRequest request = new UserHealthConditionUpdateRequest(newUserId, newHealthConditionId);

		UserHealthCondition spyUserHealthCondition = spy(userHealthCondition);

		given(userHealthConditionRepository.findById(userHealthConditionId)).willReturn(Optional.of(spyUserHealthCondition));
		given(userRepository.findById(newUserId)).willReturn(Optional.of(newUser));
		given(healthConditionRepository.findById(newHealthConditionId)).willReturn(Optional.of(newHealthCondition));

		// When
		UserHealthCondition result = userHealthConditionService.updateUserHealthCondition(userHealthConditionId, request);

		// Then
		assertThat(result).isNotNull();

		then(spyUserHealthCondition).should(times(1)).update(newUser, newHealthCondition);
		then(userHealthConditionRepository).should(times(1)).findById(userHealthConditionId);
		then(userRepository).should(times(1)).findById(newUserId);
		then(healthConditionRepository).should(times(1)).findById(newHealthConditionId);
		then(userHealthConditionRepository).should(never()).save(any(UserHealthCondition.class));
	}

	@Test
	@DisplayName("사용자 건강 상태 삭제 성공")
	void deleteUserHealthCondition_success() {
		// Given


		// When
		userHealthConditionService.deleteUserHealthCondition(userHealthConditionId);

		// Then
		then(userHealthConditionRepository).should(times(1)).deleteById(userHealthConditionId);
	}
}
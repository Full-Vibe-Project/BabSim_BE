package com.babsim.babsimbackend.domain.health.service;

import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import com.babsim.babsimbackend.domain.health.exception.HealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.exception.UserHealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.repository.HealthConditionRepository;
import com.babsim.babsimbackend.domain.health.repository.UserHealthConditionRepository;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.exception.UserNotFoundException;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserHealthConditionService {

	private final UserHealthConditionRepository userHealthConditionRepository;
	private final UserRepository userRepository;
	private final HealthConditionRepository healthConditionRepository;

	@Transactional
	public UserHealthCondition createUserHealthCondition(UserHealthConditionCreateRequest request) {
		User user = userRepository.findById(request.userId())
			.orElseThrow(UserNotFoundException::new);
		HealthCondition healthCondition = healthConditionRepository.findById(request.healthConditionId())
			.orElseThrow(HealthConditionNotFoundException::new);

		UserHealthCondition userHealthCondition = UserHealthCondition.builder()
			.user(user)
			.healthCondition(healthCondition)
			.build();
		return userHealthConditionRepository.save(userHealthCondition);
	}

	public UserHealthCondition getUserHealthConditionById(Long id) {
		return userHealthConditionRepository.findById(id)
			.orElseThrow(UserHealthConditionNotFoundException::new);
	}

	public List<UserHealthCondition> getAllUserHealthConditions() {
		return userHealthConditionRepository.findAll();
	}

	@Transactional
	public UserHealthCondition updateUserHealthCondition(Long id, UserHealthConditionUpdateRequest request) {
		UserHealthCondition userHealthCondition = getUserHealthConditionById(id);
		User user = userRepository.findById(request.userId())
			.orElseThrow(UserNotFoundException::new);
		HealthCondition healthCondition = healthConditionRepository.findById(request.healthConditionId())
			.orElseThrow(HealthConditionNotFoundException::new);

		userHealthCondition.update(user, healthCondition);
		return userHealthCondition;
	}

	@Transactional
	public void deleteUserHealthCondition(Long id) {
		userHealthConditionRepository.deleteById(id);
	}
}

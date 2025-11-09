package com.babsim.babsimbackend.domain.health.service;

import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.exception.HealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.repository.HealthConditionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthConditionService {

	private final HealthConditionRepository healthConditionRepository;

	@Transactional
	public HealthCondition createHealthCondition(HealthConditionCreateRequest request) {
		HealthCondition healthCondition = HealthCondition.builder()
			.name(request.name())
			.type(request.type())
			.build();
		return healthConditionRepository.save(healthCondition);
	}

	public HealthCondition getHealthConditionById(Long id) {
		return healthConditionRepository.findById(id)
			.orElseThrow(HealthConditionNotFoundException::new);
	}

	public List<HealthCondition> getAllHealthConditions() {
		return healthConditionRepository.findAll();
	}

	@Transactional
	public HealthCondition updateHealthCondition(Long id, HealthConditionUpdateRequest request) {
		HealthCondition healthCondition = getHealthConditionById(id);
		healthCondition.update(request.name(), request.type());
		return healthConditionRepository.save(healthCondition);
	}

	@Transactional
	public void deleteHealthCondition(Long id) {
		healthConditionRepository.deleteById(id);
	}
}

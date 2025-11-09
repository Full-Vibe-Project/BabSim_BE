package com.babsim.babsimbackend.domain.health.repository;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthConditionRepository extends JpaRepository<HealthCondition, Long> {
}

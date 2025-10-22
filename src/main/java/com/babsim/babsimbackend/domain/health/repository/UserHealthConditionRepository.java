package com.babsim.babsimbackend.domain.health.repository;

import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHealthConditionRepository extends JpaRepository<UserHealthCondition, Long> {
}

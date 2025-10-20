package com.babsim.babsimbackend.domain.user.repository;

import com.babsim.babsimbackend.domain.user.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

	// 건강상태까지 한방에 조회하고 싶을 때 사용 (N+1 회피)
	@EntityGraph(attributePaths = {"userHealthConditions", "userHealthConditions.healthCondition"})
	Optional<User> findWithHealthConditionsById(UUID id);

	boolean existsByNameAndAge(String name, Integer age);
}
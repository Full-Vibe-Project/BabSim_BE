package com.babsim.babsimbackend.domain.health.entity;

import static lombok.AccessLevel.*;

import com.babsim.babsimbackend.domain.auth.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Table(
	name = "userHealthConditions",
	uniqueConstraints = @UniqueConstraint(name="uqUserHealth", columnNames={"userId","healthConditionId"})
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class UserHealthCondition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "healthConditionId", nullable = false)
	private HealthCondition healthCondition;

	@Builder
	public UserHealthCondition(User user, HealthCondition healthCondition) {
		this.user = user;
		this.healthCondition = healthCondition;
	}
}
package com.babsim.babsimbackend.domain.user.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import com.babsim.babsimbackend.domain.user.dto.request.UserDto;
import com.babsim.babsimbackend.domain.user.enums.Gender;
import com.babsim.babsimbackend.domain.user.enums.GoalType;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@JdbcTypeCode(SqlTypes.CHAR)
	@Column(length = 36, nullable = false, updatable = false)
	private UUID id;

	@Column(nullable = false)
	private String name;

	private Integer age;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(precision = 5, scale = 2)
	private BigDecimal height;

	@Column(precision = 5, scale = 2)
	private BigDecimal weight;

	@Enumerated(EnumType.STRING)
	private GoalType goal;

	private String notionAccessToken;

	@OneToMany(mappedBy = "user")
	private List<UserHealthCondition> userHealthConditions = new ArrayList<>();

	@Builder
	public User(String name, Integer age, Gender gender, BigDecimal height, BigDecimal weight, GoalType goal,
		String notionAccessToken) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.goal = goal;
		this.notionAccessToken = notionAccessToken;
	}

	public void update(UserDto.Update request) {
		if (request.name() != null) {
			this.name = request.name();
		}
		if (request.age() != null) {
			this.age = request.age();
		}
		if (request.gender() != null) {
			this.gender = request.gender();
		}
		if (request.height() != null) {
			this.height = request.height();
		}
		if (request.weight() != null) {
			this.weight = request.weight();
		}
		if (request.goal() != null) {
			this.goal = request.goal();
		}
		if (request.notionAccessToken() != null) {
			this.notionAccessToken = request.notionAccessToken();
		}
	}
}

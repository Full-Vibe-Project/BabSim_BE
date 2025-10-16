package com.babsim.babsimbackend.domain.auth.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import com.babsim.babsimbackend.domain.auth.enums.GoalType;
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

	private Character sex;

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
	public User(String name, Integer age, Character sex, BigDecimal height, BigDecimal weight, GoalType goal,
		String notionAccessToken) {
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.height = height;
		this.weight = weight;
		this.goal = goal;
		this.notionAccessToken = notionAccessToken;
	}

	public void changeName(String name) { this.name = name; }
	public void changeAge(Integer age) { this.age = age; }
	public void changeSex(Character sex) { this.sex = sex; }
	public void changeHeight(BigDecimal height) { this.height = height; }
	public void changeWeight(BigDecimal weight) { this.weight = weight; }
	public void changeGoal(GoalType goal) { this.goal = goal; }
	public void changeNotionAccessToken(String token) { this.notionAccessToken = token; }
}

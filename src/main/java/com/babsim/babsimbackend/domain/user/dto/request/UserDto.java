package com.babsim.babsimbackend.domain.user.dto.request;

import com.babsim.babsimbackend.domain.user.enums.GoalType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class UserDto {

	public record Create(
		@NotBlank @Size(max = 50) String name,
		Integer age,
		Character sex,
		BigDecimal height,
		BigDecimal weight,
		GoalType goal,
		String notionAccessToken
	) {
	}

	public record Update(
		@Size(max = 50) String name,
		Integer age,
		Character sex,
		BigDecimal height,
		BigDecimal weight,
		GoalType goal,
		String notionAccessToken
	) {
	}

	public record Response(
		UUID id,
		String name,
		Integer age,
		Character sex,
		BigDecimal height,
		BigDecimal weight,
		GoalType goal,
		String notionAccessToken
	) {
	}
}
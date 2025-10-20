package com.babsim.babsimbackend.domain.user.dto.request;

import com.babsim.babsimbackend.domain.user.enums.Gender;
import com.babsim.babsimbackend.domain.user.enums.GoalType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public class UserDto {

	public record Create(
		@Schema(description = "사용자 이름", example = "홍길동")
		@NotBlank @Size(max = 50) String name,

		@Schema(description = "나이", example = "30")
		Integer age,

		@Schema(description = "성별", example = "MALE") // Enum constant name
		Gender gender,

		@Schema(description = "키 (cm)", example = "180.5")
		BigDecimal height,

		@Schema(description = "몸무게 (kg)", example = "75.2")
		BigDecimal weight,

		@Schema(description = "운동 목표", example = "WEIGHT_LOSS")
		GoalType goal,

		@Schema(description = "노션 연동 토큰", example = "secret_a1b2c3d4e5f6...")
		String notionAccessToken
	) {
	}

	public record Update(
		@Size(max = 50) String name,
		Integer age,
		Gender gender,
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
		Gender gender,
		BigDecimal height,
		BigDecimal weight,
		GoalType goal,
		String notionAccessToken
	) {
	}
}
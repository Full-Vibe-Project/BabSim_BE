package com.babsim.babsimbackend.domain.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class UserControllerTest {

	@Autowired MockMvc mvc;

	@Test
	@DisplayName("POST → Location으로 GET → PATCH → DELETE")
	void end_to_end_crud() throws Exception {
		// CREATE
		String body = """
        { "name":"홍길동", "age":29, "sex":"M", "goal":"GENERAL" }
        """;
		var createRes = mvc.perform(post("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isCreated())
			.andReturn();

		String location = createRes.getResponse().getHeader("Location");
		assertThat(location).isNotBlank();

		// READ
		mvc.perform(get(location))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("홍길동"));

		// UPDATE (PATCH)
		String patchBody = """
        { "name":"홍길동2", "goal":"WEIGHT_GAIN" }
        """;
		mvc.perform(patch(location)
				.contentType(MediaType.APPLICATION_JSON)
				.content(patchBody))
			.andExpect(status().isNoContent());

		mvc.perform(get(location))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("홍길동2"))
			.andExpect(jsonPath("$.goal").value("WEIGHT_GAIN"));

		// DELETE
		mvc.perform(delete(location))
			.andExpect(status().isNoContent());

		mvc.perform(get(location))
			.andExpect(status().isNotFound()) // 404 Not Found 명시
			.andExpect(jsonPath("$.code").value("USER_NOT_FOUND")); // 에러 코드 검증
	}
}
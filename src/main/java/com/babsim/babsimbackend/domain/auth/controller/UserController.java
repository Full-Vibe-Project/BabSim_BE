package com.babsim.babsimbackend.domain.auth.controller;

import com.babsim.babsimbackend.domain.auth.dto.request.UserDto;
import com.babsim.babsimbackend.domain.auth.service.UserService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody UserDto.Create req) {
		final UUID id = userService.create(req);
		return ResponseEntity.created(URI.create("/api/users/" + id)).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto.Response> get(@PathVariable UUID id) {
		return ResponseEntity.ok(userService.get(id));
	}

	@GetMapping
	public ResponseEntity<Page<UserDto.Response>> list(Pageable pageable) {
		return ResponseEntity.ok(userService.list(pageable));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UserDto.Update req) {
		userService.update(id, req);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
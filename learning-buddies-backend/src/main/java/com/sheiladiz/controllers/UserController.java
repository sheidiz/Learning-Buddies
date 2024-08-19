package com.sheiladiz.controllers;

import java.util.List;

import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.User;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private final UserService userService;
	private final UserMapper userMapper;

	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<User> users = userService.allUsers();
		return ResponseEntity.ok(userMapper.userEntitiesToUserDTOs(users));
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = UserDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found.",
					content = @Content)})
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		try {
			User user = userService.getUserById(id);
			return ResponseEntity.ok(userMapper.toDTO(user));
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = UserDTO.class)) }),
			@ApiResponse(responseCode = "409", description = "User not found.",
					content = @Content)})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
		try {
			User updatedUser = userService.updateUser(id, userDTO);
			return ResponseEntity.ok(userMapper.toDTO(updatedUser));
		} catch (UserNotFoundException | EmailAlreadyRegisteredException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "User deleted successfully.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found.",
					content = @Content)})
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok("User deleted successfully");
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

}

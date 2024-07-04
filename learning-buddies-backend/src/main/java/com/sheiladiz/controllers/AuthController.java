package com.sheiladiz.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.dtos.LoginRequest;
import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.exceptions.user.InvalidUserCredentialsException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.services.UserService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			userService.isUserExistsByEmail(registerRequest.getEmail());
		} catch (EmailAlreadyRegisteredException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}

		if (registerRequest.getAuthProvider() == null) {
			registerRequest.setAuthProvider("local");
		}
		UserEntity userEntity = userMapper.registerRequestToUserEntity(registerRequest);
		UserEntity savedUser = userService.saveUser(userEntity);
		UserDTO savedUserDto = userMapper.userEntityToUserDTO(savedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			UserEntity existingUser = userService.findUserByEmail(loginRequest.getEmail());
			userService.validateCredentials(existingUser, loginRequest.getPassword());

			UserDTO userDTO = userMapper.userEntityToUserDTO(existingUser);
			return ResponseEntity.ok(userDTO);
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (InvalidUserCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
		}
	}
}

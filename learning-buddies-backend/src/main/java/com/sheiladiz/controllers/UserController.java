package com.sheiladiz.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.dtos.ChangePasswordRequest;
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
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@PostMapping()
	public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest registerRequest,
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

	@GetMapping()
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserEntity> userEntities = userService.allUsers();
		List<UserDTO> userDTOs = userMapper.userEntitiesToUserDTOs(userEntities);
		return ResponseEntity.ok(userDTOs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> apiShowUserById(@PathVariable("id") Long id) {
		try {
			UserEntity userEntity = userService.findUserById(id);
			UserDTO userDto = userMapper.userEntityToUserDTO(userEntity);
			return ResponseEntity.ok(userDto);
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
		try {
			UserEntity existingUser = userService.findUserById(id);
			if (userDTO.getEmail() != null) {
				userService.isUserExistsByEmail(userDTO.getEmail());
				existingUser.setEmail(userDTO.getEmail());
			}

			if (userDTO.getAuthProvider() != null) {
				existingUser.setAuthProvider(userDTO.getAuthProvider());
			}

			UserEntity updatedUser = userService.saveUser(existingUser);
			UserDTO updatedUserDto = userMapper.userEntityToUserDTO(updatedUser);
			return ResponseEntity.ok(updatedUserDto);
		} catch (UserNotFoundException | EmailAlreadyRegisteredException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@PutMapping("/update-password/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
			@RequestBody ChangePasswordRequest changePasswordRequest) {
		try {
			UserEntity existingUser = userService.findUserById(id);
			userService.validateCredentials(existingUser, changePasswordRequest.getOldPassword());

			existingUser.setPassword(changePasswordRequest.getNewPassword());
			UserEntity updatedUser = userService.saveUser(existingUser);
			UserDTO updatedUserDto = userMapper.userEntityToUserDTO(updatedUser);
			return ResponseEntity.ok(updatedUserDto);
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (InvalidUserCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
		}
	}

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

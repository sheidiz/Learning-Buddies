package com.sheiladiz.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.sheiladiz.dtos.*;
import com.sheiladiz.exceptions.user.InvalidUserCredentialsException;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.User;
import com.sheiladiz.services.AuthenticationService;
import com.sheiladiz.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;

import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	private final JwtService jwtService;
	private final AuthenticationService authenticationService;
	private final UserMapper userMapper;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserMapper userMapper) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
		this.userMapper = userMapper;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			User registeredUser = authenticationService.signUp(registerRequest);
			return ResponseEntity.ok(userMapper.toDTO(registeredUser));
		} catch (EmailAlreadyRegisteredException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
		try {
			User authenticatedUser = authenticationService.authenticate(loginRequest);
			String jwtToken = jwtService.generateToken(authenticatedUser);
			LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
			return ResponseEntity.ok(loginResponse);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
		}
	}

	@PutMapping("/change-password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User authenticatedUser = (User) authentication.getPrincipal();

			authenticationService.changePassword(authenticatedUser, changePasswordRequest.getCurrentPassword(), changePasswordRequest.getNewPassword());

			return ResponseEntity.ok("Contraseña actualizada correctamente.");
		} catch (InvalidUserCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error al actualizar la contraseña");
		}
	}

}

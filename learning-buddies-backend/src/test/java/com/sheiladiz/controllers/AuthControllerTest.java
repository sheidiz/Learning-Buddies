package com.sheiladiz.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheiladiz.dtos.LoginRequest;
import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.services.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@InjectMocks
	private AuthController authController;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testRegisterUser_ShouldReturnCreatedUserDTO() throws Exception {
		RegisterRequest registerRequest = new RegisterRequest("test@example.com", "password123", "local");

		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@example.com");

		when(userService.registerUser(any(RegisterRequest.class))).thenReturn(userDTO);

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest))).andExpect(status().isCreated());
	}

	@Test
	public void testRegisterUser_ShouldReturnConflictWhenEmailAlreadyRegistered() throws Exception {
		RegisterRequest registerRequest = new RegisterRequest("test@example.com", "password123", "local");

		doThrow(new EmailAlreadyRegisteredException("Email already registered")).when(userService)
				.isUserExistsByEmail(any(String.class));

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest))).andExpect(status().isConflict());
	}

	@Test
	public void testRegisterUser_ShouldReturnBadRequestWhenInvalidRequest() throws Exception {
		RegisterRequest registerRequest = new RegisterRequest("", "password123", "local");

		mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registerRequest))).andExpect(status().isBadRequest());
	}

	@Test
	public void testLoginUser_ShouldReturnUserDTO() throws Exception {
		LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");

		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@example.com");

		when(userService.loginUser(any(LoginRequest.class))).thenReturn(userDTO);

		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk());
	}

	@Test
	public void testLoginUser_ShouldReturnUnauthorizedWhenInvalidCredentials() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("test@example.com");
		loginRequest.setPassword("wrongpassword");

		when(userService.loginUser(any(LoginRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));

		mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isUnauthorized());
	}

}

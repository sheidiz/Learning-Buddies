package com.sheiladiz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserServiceImpl userService;

	private RegisterRequest registerRequest;
	private UserEntity user;
	private UserDTO userDTO;

	@BeforeEach
	public void setup() {
		registerRequest = new RegisterRequest("test@example.com", "password123", "local");

		user = new UserEntity();
		user.setEmail("test@example.com");
		user.setPassword("password123");

		userDTO = new UserDTO();
		userDTO.setEmail("test@example.com");
	}

	@Test
	public void whenValidRegisterRequest_thenUserIsRegistered() {
		when(userRepository.save(any(UserEntity.class))).thenReturn(user);
		when(userMapper.registerRequestToEntity(any(RegisterRequest.class))).thenReturn(user);
		when(userMapper.toDTO(any(UserEntity.class))).thenReturn(userDTO);
		
		UserDTO result = userService.registerUser(registerRequest);
		
		assertEquals("test@example.com", result.getEmail());
	}

	@Test
	public void whenEmailAlreadyExists_thenThrowException() {
		when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
		
		assertThrows(EmailAlreadyRegisteredException.class, () -> {
			userService.isUserExistsByEmail("test@example.com");
		});
	}

	@Test
	public void whenUserNotFoundById_thenThrowException() {
		when(userRepository.findById(1L)).thenThrow(new UserNotFoundException("User not found"));
		
		assertThrows(UserNotFoundException.class, () ->{
			userService.getUserById(1L);
		});
	}
}

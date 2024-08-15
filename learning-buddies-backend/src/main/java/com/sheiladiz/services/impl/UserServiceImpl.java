package com.sheiladiz.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.LoginRequest;
import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.exceptions.user.InvalidUserCredentialsException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	public List<User> allUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
		return user;
	}

	public User getUserByEmail(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Usuario con email [" + email + "] no encontrado"));
		return user;
	}

	public User getUserByProfileId(Long id) {
		User user = userRepository.findByProfileId(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
		return user;
	}

	public User updateUser(Long id, UserDTO userDTO) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));

		if (userDTO.getEmail() != null) {
			existingUser.setEmail(userDTO.getEmail());
		}

		if (userDTO.getAuthProvider() != null) {
			existingUser.setAuthProvider(userDTO.getAuthProvider());
		}

		User updatedUser = userRepository.save(existingUser);
		return updatedUser;
	}

	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
		userRepository.delete(user);
	}

	public void isUserExistsByEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new EmailAlreadyRegisteredException("Email ya registrado: " + email);
		}
	}
}

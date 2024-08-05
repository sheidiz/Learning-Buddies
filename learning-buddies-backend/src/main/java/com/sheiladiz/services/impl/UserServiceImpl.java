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
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	// @Autowired
	// private PasswordEncoder passwordEncoder;

	public UserDTO registerUser(RegisterRequest registerRequest) {
		// registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		UserEntity userEntity = userMapper.registerRequestToEntity(registerRequest);
		UserEntity savedUser = userRepository.save(userEntity);
		return userMapper.toDTO(savedUser);
	}

	public UserDTO loginUser(LoginRequest loginRequest) {
		Optional<UserEntity> userEntity = userRepository.findByEmail(loginRequest.getEmail());
		// !(passwordEncoder.matches(loginRequest, user.getPassword())) invalid
		if (userEntity.isPresent() && userEntity.get().getPassword().equals(loginRequest.getPassword())) {
			return userMapper.toDTO(userEntity.get());
		} else {
			throw new InvalidUserCredentialsException("Email o contraseña incorrectos.");
		}
	}

	public UserDTO saveUser(UserDTO newUser) {
		UserEntity userEntity = userMapper.toEntity(newUser);
		UserEntity savedUser = userRepository.save(userEntity);
		return userMapper.toDTO(savedUser);
	}

	public List<UserDTO> allUsers() {
		return userMapper.userEntitiesToUserDTOs(userRepository.findAll());
	}

	public UserDTO getUserById(Long id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
		return userMapper.toDTO(userEntity);
	}

	public UserEntity getUserEntityById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
	}

	public UserDTO getUserByProfileId(Long id) {
		UserEntity userEntity = userRepository.findByProfileId(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
		return userMapper.toDTO(userEntity);
	}

	public UserEntity getUserEntityByProfileId(Long id) {
		return userRepository.findByProfileId(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
	}

	public UserDTO getUserByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Usuario con email [" + email + "] no encontrado"));
		return userMapper.toDTO(userEntity);
	}

	public UserDTO updateUser(Long id, UserDTO userDTO) {
		UserEntity existingUser = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));

		if (userDTO.getEmail() != null) {
			existingUser.setEmail(userDTO.getEmail());
		}

		if (userDTO.getAuthProvider() != null) {
			existingUser.setAuthProvider(userDTO.getAuthProvider());
		}

		UserEntity updatedUser = userRepository.save(existingUser);
		return userMapper.toDTO(updatedUser);
	}

	/*
	 * public void changePassword(Long id, ChangePasswordRequest
	 * changePasswordRequest) throws UserNotFoundException,
	 * InvalidUserCredentialsException { UserEntity user =
	 * userRepository.findById(id) .orElseThrow(() -> new
	 * UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
	 * 
	 * // Validar la contraseña actual /*if
	 * (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),
	 * user.getPassword())) { throw new
	 * InvalidUserCredentialsException("Credenciales invalidas."); }
	 * 
	 * // Actualizar la contraseña
	 * user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword(
	 * ))); userRepository.save(user); }
	 */

	public void deleteUser(Long id) {
		UserEntity userEntity = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
		userRepository.delete(userEntity);
	}

	public void isUserExistsByEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new EmailAlreadyRegisteredException("Email ya registrado: " + email);
		}
	}
}

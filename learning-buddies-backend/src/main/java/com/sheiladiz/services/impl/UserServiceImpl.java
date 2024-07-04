package com.sheiladiz.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.exceptions.user.InvalidUserCredentialsException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	// @Autowired
	// private PasswordEncoder passwordEncoder;

	/*
	 * public UserEntity registerUser(UserEntity user) { UserEntity foundUser =
	 * userRepository.findByEmail(user.getEmail()).orElse(null);
	 * 
	 * if (foundUser != null) { throw new
	 * IllegalArgumentException("El usuario ya existe".concat(foundUser.toString()))
	 * ; } // user.setPassword(passwordEncoder.encode(user.getPassword())); return
	 * userRepository.save(user); }
	 */

	/*
	 * public UserEntity loginUser(String email, String password) { UserEntity user
	 * = userRepository.findByEmail(email).orElse(null);
	 * 
	 * // if (user == null || !passwordEncoder.matches(password,
	 * user.getPassword())) { if (user == null ||
	 * !password.equals(user.getPassword())) { throw new
	 * IllegalArgumentException("Usuario y/o contraseña invalidos."); }
	 * 
	 * return user; }
	 */

	public void isUserExistsByEmail(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new EmailAlreadyRegisteredException("Email ya registrado: " + email);
		}
	}

	public UserEntity saveUser(UserEntity newUser) {
		return userRepository.save(newUser);
	}

	public List<UserEntity> allUsers() {
		return userRepository.findAll();
	}

	public UserEntity findUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuario con id [" + id + "] no encontrado"));
	}

	public UserEntity findUserByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Usuario con email [" + email + "] no encontrado"));
	}

	public void validateCredentials(UserEntity user, String password) {
		if (!user.getPassword().equals(password)) {
			throw new InvalidUserCredentialsException("Credenciales invalidas.");
		}
	}

	public void deleteUser(Long id) {
		UserEntity user = findUserById(id);
		userRepository.delete(user);
	}

}

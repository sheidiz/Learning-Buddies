package com.sheiladiz.services;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	//@Autowired
	//private PasswordEncoder passwordEncoder;

	public UserEntity registerUser(UserEntity user) {
		UserEntity foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
		
		if (foundUser!=null) {
			throw new IllegalArgumentException("El usuario ya existe".concat(foundUser.toString()));
		}
		//user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public UserEntity loginUser(String email, String password) {
		UserEntity user = userRepository.findByEmail(email).orElse(null);

		//if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
		if (user == null || !password.equals(user.getPassword())) {
			throw new IllegalArgumentException("Usuario y/o contraseña invalidos.");
		}

		return user;
	}

	public UserEntity updateUser(UserEntity user) {
		if (userRepository.existsById(user.getId())) {
			return userRepository.save(user);
		} else {
			throw new IllegalArgumentException("Usuario no encontrado.");
		}
	}

	public UserEntity getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

}

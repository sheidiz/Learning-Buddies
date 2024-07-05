package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.ChangePasswordRequest;
import com.sheiladiz.dtos.LoginRequest;
import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.models.UserEntity;

public interface UserService {

	UserDTO registerUser(RegisterRequest registerRequest);
	UserDTO loginUser(LoginRequest loginRequest);

	UserDTO saveUser(UserDTO newUser);
	List<UserDTO> allUsers();
	UserDTO getUserById(Long id);
	UserEntity getUserEntityById(Long id);
	UserDTO getUserByProfileId(Long id);
	UserEntity getUserEntityByProfileId(Long id);
	UserDTO getUserByEmail(String email);
	UserDTO updateUser(Long id, UserDTO userDTO);
	//UserDTO changePassword(Long id, ChangePasswordRequest changePasswordRequest);
	void deleteUser(Long id);
	void isUserExistsByEmail(String email);
}

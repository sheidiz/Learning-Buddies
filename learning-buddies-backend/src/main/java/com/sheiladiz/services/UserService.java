package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.models.UserEntity;

public interface UserService {

	// CREATE / UPDATE
	UserEntity saveUser(UserEntity newUser);

	// READ
	void isUserExistsByEmail(String email);
	UserEntity findUserById(Long id);
	UserEntity findUserByEmail(String email);
	List<UserEntity> allUsers();
	void validateCredentials(UserEntity user, String password);

	// DELETE
	void deleteUser(Long id);

}

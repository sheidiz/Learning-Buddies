package com.sheiladiz.services;

import java.util.List;
import java.util.Optional;

import com.sheiladiz.models.UserEntity;

public interface UserService {

	// CREATE
	UserEntity saveUser(UserEntity newUser);

	// READ
	boolean isUserExistsByEmail(String email);
	Optional<UserEntity> findUserById(Long id);
	Optional<UserEntity> findUserByEmail(String email);
	List<UserEntity> allUsers();

	// UPDATE
	UserEntity updateUser(UserEntity user);

	// DELETE
	void deleteUser(Long id);

}

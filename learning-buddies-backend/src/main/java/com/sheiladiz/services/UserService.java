package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.models.User;

public interface UserService {
	List<User> allUsers();
	User getUserById(Long id);
	User getUserByEmail(String email);
	User getUserByProfileId(Long id);
	User updateUser(Long id, ResponseUserDto responseUserDto);
	void deleteUser(Long id);
	void isUserExistsByEmail(String email);
}

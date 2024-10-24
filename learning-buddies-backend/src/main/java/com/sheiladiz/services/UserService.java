package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.user.RequestUpdateUserDto;
import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.models.User;

public interface UserService {
	List<ResponseUserDto> allUsers();
	ResponseUserDto getUserById(String id);
    ResponseUserDto getUserDtoByEmail(String email);
    User getUserByEmail(String email);
    ResponseUserDto getUserByUsername(String username);
    ResponseUserDto updateUser(String id, RequestUpdateUserDto requestUpdateUserDto);
	void deleteUser(String id);
	void isUserExistsByEmail(String email);
}

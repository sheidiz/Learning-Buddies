package com.sheiladiz.services;

import com.sheiladiz.dtos.user.RequestLoginDto;
import com.sheiladiz.dtos.user.RequestRegisterDto;
import com.sheiladiz.dtos.user.ResponseLoginDto;
import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.models.User;

public interface AuthenticationService {
    ResponseUserDto signUp(RequestRegisterDto registerUserDto);
    ResponseLoginDto login(RequestLoginDto requestLoginDto);
    void changePassword(User authenticatedUser, String currentPassword, String newPassword);
}

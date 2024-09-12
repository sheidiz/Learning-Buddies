package com.sheiladiz.services;

import com.sheiladiz.dtos.user.LoginRequest;
import com.sheiladiz.dtos.user.RegisterRequest;
import com.sheiladiz.models.User;

public interface AuthenticationService {
    User signUp(RegisterRequest registerUserDto);
    User authenticate(LoginRequest loginRequest);
    void changePassword(User authenticatedUser, String currentPassword, String newPassword);
}

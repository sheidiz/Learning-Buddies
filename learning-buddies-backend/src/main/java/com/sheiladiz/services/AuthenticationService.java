package com.sheiladiz.services;

import com.sheiladiz.dtos.LoginRequest;
import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.models.User;

public interface AuthenticationService {
    User signUp(RegisterRequest registerUserDto);
    User authenticate(LoginRequest loginRequest);
    void changePassword(User authenticatedUser, String currentPassword, String newPassword);
}

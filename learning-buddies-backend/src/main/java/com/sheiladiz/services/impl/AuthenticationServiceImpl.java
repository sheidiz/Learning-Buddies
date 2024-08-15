package com.sheiladiz.services.impl;

import com.sheiladiz.dtos.LoginRequest;
import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.exceptions.user.InvalidUserCredentialsException;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public User signUp(RegisterRequest registerUserDto){
        User user = User.builder()
                .email(registerUserDto.getEmail())
                .authProvider(registerUserDto.getAuthProvider())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        if(userRepository.findByEmail(registerUserDto.getEmail()).isPresent()){
            throw new RuntimeException("Email ya se encuentra registrado");
        }

        try {
            User savedUser = userRepository.save(user);
            return savedUser;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public User authenticate(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()-> new RuntimeException("User not found"));

        if(!user.isEnabled()){
            throw new EmailAlreadyRegisteredException("Account is disabled, try to contact support.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        return user;
    }

    @Override
    public void changePassword(User authenticatedUser, String currentPassword, String newPassword) {
        if (!passwordEncoder.matches(currentPassword, authenticatedUser.getPassword())) {
            throw new InvalidUserCredentialsException("Contraseña actual ingresada es incorrecta.");
        }

        authenticatedUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(authenticatedUser);
    }
}

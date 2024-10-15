package com.sheiladiz.services.impl;

import com.sheiladiz.dtos.user.LoginRequest;
import com.sheiladiz.dtos.user.RegisterRequest;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.exceptions.InvalidUserCredentialsException;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signUp(RegisterRequest registerUserDto) {
        User user = User.builder()
                .email(registerUserDto.getEmail())
                .authProvider(registerUserDto.getAuthProvider())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        if (userRepository.findByEmail(registerUserDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email ya se encuentra registrado");
        }

        return userRepository.save(user);
    }

    public User authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            return user;
        } catch (BadCredentialsException ex) {
            throw new InvalidUserCredentialsException("Usuario y/o contraseña invalidos.");
        }
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

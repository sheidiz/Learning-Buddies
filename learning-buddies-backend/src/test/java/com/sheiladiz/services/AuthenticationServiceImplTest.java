package com.sheiladiz.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.sheiladiz.dtos.user.LoginRequest;
import com.sheiladiz.dtos.user.RegisterRequest;
import com.sheiladiz.exceptions.InvalidUserCredentialsException;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void shouldSignUpUserSuccessfully_whenEmailIsNotRegistered() {
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "password123", "local");

        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        User result = authenticationService.signUp(registerRequest);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowResourceAlreadyExistsException_whenEmailIsAlreadyRegistered() {
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "password123", "local");

        when(userRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(new User()));

        assertThrows(ResourceAlreadyExistsException.class, () -> authenticationService.signUp(registerRequest));
    }

    @Test
    void shouldAuthenticateUserSuccessfully_whenCredentialsAreValid() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password123");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));

        User result = authenticationService.authenticate(loginRequest);

        assertEquals(user, result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void shouldThrowResourceNotFoundException_whenUserDoesNotExist() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("nonexisting@example.com");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authenticationService.authenticate(loginRequest));
    }

    @Test
    void shouldThrowInvalidUserCredentialsException_whenCredentialsAreInvalid() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "wrongpassword");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        doThrow(new BadCredentialsException(("Invalid credentials"))).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(InvalidUserCredentialsException.class, () -> authenticationService.authenticate(loginRequest));
    }

    @Test
    void shouldChangePasswordSuccessfully_whenCurrentPasswordIsValid() {
        User user = new User();
        user.setPassword("encondedCurrentPassword");

        String currentPassword = "currentPassword";
        String newPassword = "newPassword";

        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        authenticationService.changePassword(user, currentPassword, newPassword);

        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowInvalidUserCredentialsException_whenCurrentPasswordIsInvalid() {
        User user = new User();
        user.setPassword("encondedCurrentPassword");

        String currentPassword = "currentPassword";
        String newPassword = "newPassword";

        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(false);

        assertThrows(InvalidUserCredentialsException.class, () -> authenticationService.changePassword(user, currentPassword, newPassword));
    }
}

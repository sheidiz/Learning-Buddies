package com.sheiladiz.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sheiladiz.dtos.LoginRequest;
import com.sheiladiz.dtos.LoginResponse;
import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.InvalidUserCredentialsException;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.User;
import com.sheiladiz.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authService;

    @InjectMocks
    private AuthenticationController authController;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private LoginResponse loginResponse;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        registerRequest = new RegisterRequest("test@example.com", "password123", "local");
        user = User.builder()
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .authProvider(registerRequest.getAuthProvider())
                .build();

        userDTO = UserDTO.builder()
                .email(user.getEmail())
                .authProvider(user.getAuthProvider())
                .build();
        loginRequest = new LoginRequest(registerRequest.getEmail(), registerRequest.getPassword());
        loginResponse = new LoginResponse("1234", 36000, userDTO);
    }

    @Test
    public void testRegisterUser_ShouldReturnCreatedUserDTO() throws Exception {
        when(authService.signUp(any(RegisterRequest.class))).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(userDTO.getEmail()));
    }

    @Test
    public void testRegisterUser_ShouldReturnConflictWhenEmailAlreadyRegistered() throws Exception {
        doThrow(new ResourceAlreadyExistsException("Email ya se encuentra registrado.")).when(authService.signUp(any()));

        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testLoginUser_ShouldReturnLoginResponse() throws Exception {
        when(authService.authenticate(any(LoginRequest.class))).thenReturn(user);

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(loginResponse.getToken()));
    }

    @Test
    public void testLoginUser_ShouldReturnUnauthorizedWhenInvalidCredentials() throws Exception {
        when(authService.authenticate(any(LoginRequest.class))).thenThrow(new InvalidUserCredentialsException("Usuario y/o contraseña invalidos."));

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

}

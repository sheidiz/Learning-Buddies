package com.sheiladiz.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.sheiladiz.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.services.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private UserDTO userDTO;
	private User user;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setEmail("test@example.com");

		userDTO = new UserDTO();
		userDTO.setEmail("test@example.com");
	}

	@Test
    public void testGetUserById_ShouldReturnUserDTO() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(userDTO)));
    }

	@Test
    public void testUpdateUser_ShouldReturnUpdatedUserDTO() throws Exception {
        when(userService.updateUser(any(Long.class), any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDTO)));
    }

	@Test
	public void testDeleteUser_ShouldReturnSuccessMessage() throws Exception {
		mockMvc.perform(delete("/api/v1/users/1")).andExpect(status().isOk())
				.andExpect(content().string("User deleted successfully"));
	}

	@Test
    public void testGetUserById_ShouldReturnNotFoundWhenUserNotFound() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/api/v1/users/1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("User not found"));
    }
}
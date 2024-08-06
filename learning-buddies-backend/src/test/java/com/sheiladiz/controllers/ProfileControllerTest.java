package com.sheiladiz.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.dtos.SkillsRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.profile.ProfileNotFoundException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.UserService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private ProfileDTO profileDTO;
	private UserDTO userDTO;
	private SkillsRequest skillsRequest;
	private UserEntity user;

	@MockBean
	private ProfileService profileService;

	@MockBean
	private UserService userService;

	@BeforeEach
	public void setup() {
		profileDTO = new ProfileDTO();
		profileDTO.setName("Test Profile");

		userDTO = new UserDTO();
		userDTO.setEmail("test@example.com");

		skillsRequest = new SkillsRequest();
		skillsRequest.setSkillsLearned(Arrays.asList("Java", "Spring"));

		user = new UserEntity();
		user.setId(1L);
		user.setEmail("test@example.com");
	}

	@Test
    public void testCreateProfile_Success() throws Exception {
        when(userService.getUserEntityById(anyLong())).thenReturn(user);
        when(profileService.saveProfile(any(ProfileDTO.class), any(UserEntity.class))).thenReturn(profileDTO);

        mockMvc.perform(post("/api/v1/profiles/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profileDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(profileDTO.getName()));
    }

	@Test
    public void testCreateProfile_UserNotFound() throws Exception {
        when(userService.getUserEntityById(anyLong())).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(post("/api/v1/profiles/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profileDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }

	@Test
    public void testGetProfileById_Success() throws Exception {
        when(profileService.getProfileById(anyLong())).thenReturn(profileDTO);

        mockMvc.perform(get("/api/v1/profiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(profileDTO.getName()));
    }

	@Test
    public void testGetProfileById_NotFound() throws Exception {
        when(profileService.getProfileById(anyLong())).thenThrow(new ProfileNotFoundException("Profile not found"));

        mockMvc.perform(get("/api/v1/profiles/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Profile not found"));
    }

	@Test
    public void testUpdateProfile_Success() throws Exception {
        when(profileService.updateProfile(anyLong(), any(ProfileDTO.class))).thenReturn(profileDTO);

        mockMvc.perform(put("/api/v1/profiles/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(profileDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(profileDTO.getName()));
    }

	@Test
    public void testAddSkillsToProfile_Success() throws Exception {
        when(profileService.addProfileSkills(anyString(), anyLong(), anyList())).thenReturn(profileDTO);

        mockMvc.perform(put("/api/v1/profiles/1/addSkills")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(skillsRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(profileDTO.getName()));
    }

	@Test
	public void testDeleteProfile_Success() throws Exception {
		doNothing().when(profileService).deleteProfile(anyLong());

		mockMvc.perform(delete("/api/v1/profiles/1")).andExpect(status().isOk())
				.andExpect(content().string("Perfil eliminado exitosamente"));
	}
}
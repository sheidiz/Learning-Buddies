package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.user.RegisterRequest;
import com.sheiladiz.dtos.user.UserDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.User;

@Component
public class UserMapper {
	
	@Autowired
	private ProfileMapper profileMapper;

	public UserDTO toDTO(User user) {
		UserDTO.UserDTOBuilder builder = UserDTO.builder()
				.id(user.getId())
				.email(user.getEmail())
				.authProvider(user.getAuthProvider());
		
		if (user.getProfile() != null) {
			builder.profile(profileMapper.toDTO(user.getProfile()));
		}

		return builder.build();
	}

	public User toEntity(UserDTO userDTO) {
		User.UserBuilder builder = User.builder()
				.id(userDTO.getId() != null ? userDTO.getId() : null)
				.email(userDTO.getEmail())
				.authProvider(userDTO.getAuthProvider());
		
		 if (userDTO.getProfile() != null) {
			 Profile profile = profileMapper.toEntity(userDTO.getProfile());
	         profile.setUser(builder.build());
	         builder.profile(profile);
	      }
		 
		return builder.build();
	}

	public User registerRequestToEntity(RegisterRequest registerRequest) {
		return User.builder()
				.email(registerRequest.getEmail())
				.password(registerRequest.getPassword())
				.authProvider(registerRequest.getAuthProvider())
				.build();
	}

	public List<UserDTO> userEntitiesToUserDTOs(List<User> userEntities) {
		return userEntities.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<User> userDTOsToUserEntities(List<UserDTO> userDTOs) {
		return userDTOs.stream().map(this::toEntity).collect(Collectors.toList());
	}

}
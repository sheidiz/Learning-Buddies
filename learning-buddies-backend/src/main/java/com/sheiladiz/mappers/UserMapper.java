package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;

@Component
public class UserMapper {
	
	@Autowired
	private ProfileMapper profileMapper;

	public UserDTO toDTO(UserEntity userEntity) {
		UserDTO.UserDTOBuilder builder = UserDTO.builder()
				.id(userEntity.getId())
				.email(userEntity.getEmail())
				.authProvider(userEntity.getAuthProvider());
		
		if (userEntity.getProfile() != null) {
			builder.profile(profileMapper.profileToProfileDTO(userEntity.getProfile()));
		}

		return builder.build();
	}

	public UserEntity toEntity(UserDTO userDTO) {
		UserEntity.UserEntityBuilder builder = UserEntity.builder()
				.id(userDTO.getId() != null ? userDTO.getId() : null)
				.email(userDTO.getEmail())
				.authProvider(userDTO.getAuthProvider());
		
		 if (userDTO.getProfile() != null) {
			 Profile profile = profileMapper.profileDTOToProfile(userDTO.getProfile());
	         profile.setUser(builder.build());
	         builder.profile(profile);
	      }
		 
		return builder.build();
	}

	public UserEntity registerRequestToEntity(RegisterRequest registerRequest) {
		return UserEntity.builder()
				.email(registerRequest.getEmail())
				.password(registerRequest.getPassword())
				.authProvider(registerRequest.getAuthProvider())
				.build();
	}

	public List<UserDTO> userEntitiesToUserDTOs(List<UserEntity> userEntities) {
		return userEntities.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<UserEntity> userDTOsToUserEntities(List<UserDTO> userDTOs) {
		return userDTOs.stream().map(this::toEntity).collect(Collectors.toList());
	}

}
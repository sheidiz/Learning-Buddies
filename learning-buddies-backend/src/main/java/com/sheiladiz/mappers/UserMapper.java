package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.models.UserEntity;

@Component
public class UserMapper {
	
	@Autowired
	private ProfileMapper profileMapper;

	public UserDTO userEntityToUserDTO(UserEntity userEntity) {
		UserDTO.UserDTOBuilder builder = UserDTO.builder()
				.id(userEntity.getId())
				.email(userEntity.getEmail())
				.authProvider(userEntity.getAuthProvider());
		
		if (userEntity.getProfile() != null) {
			builder.profileDTO(profileMapper.profileToProfileDTO(userEntity.getProfile()));
		}

		return builder.build();
	}

	public UserEntity userDTOToUserEntity(UserDTO userDTO) {
		UserEntity.UserEntityBuilder builder = UserEntity.builder()
				.id(userDTO.getId() != null ? userDTO.getId() : null)
				.email(userDTO.getEmail())
				.authProvider(userDTO.getAuthProvider());
		
		if (userDTO.getProfileDTO() != null) {
			builder.profile(profileMapper.profileDTOToProfile(userDTO.getProfileDTO()));
		}

		return builder.build();
	}

	public UserEntity registerRequestToUserEntity(RegisterRequest registerRequest) {
		return UserEntity.builder()
				.email(registerRequest.getEmail())
				.password(registerRequest.getPassword())
				.authProvider(registerRequest.getAuthProvider())
				.build();
	}

	public List<UserDTO> userEntitiesToUserDTOs(List<UserEntity> userEntities) {
		return userEntities.stream().map(this::userEntityToUserDTO).collect(Collectors.toList());
	}

	public List<UserEntity> userDTOsToUserEntities(List<UserDTO> userDTOs) {
		return userDTOs.stream().map(this::userDTOToUserEntity).collect(Collectors.toList());
	}

}
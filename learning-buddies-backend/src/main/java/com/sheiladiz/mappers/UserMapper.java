package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.RegisterRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.services.ProfileService;

@Component
public class UserMapper {

	@Autowired
	private ProfileService profileService;

	public UserDTO userEntityToUserDTO(UserEntity userEntity) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userEntity.getId());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setAuthProvider(userEntity.getAuthProvider());
		userDTO.setProfileId(userEntity.getProfile() != null ? userEntity.getProfile().getId() : null);
		return userDTO;
	}

	public UserEntity userDTOToUserEntity(UserDTO userDTO) {
		Profile profile = profileService.findById(userDTO.getProfileId());

		UserEntity userEntity = new UserEntity();
		if (userDTO.getId() != null)
			userEntity.setId(userDTO.getId());
		if (profile != null)
			userEntity.setProfile(profile);
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setAuthProvider(userDTO.getAuthProvider());
		return userEntity;
	}

	public List<UserDTO> userEntitiesToUserDTOs(List<UserEntity> userEntities) {
		return userEntities.stream().map(this::userEntityToUserDTO).collect(Collectors.toList());
	}

	public List<UserEntity> userDTOsToUserEntities(List<UserDTO> userDTOs) {
		return userDTOs.stream().map(this::userDTOToUserEntity).collect(Collectors.toList());
	}

	public UserEntity registerRequestToUserEntity(RegisterRequest registerRequest) {

		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(registerRequest.getEmail());
		userEntity.setPassword(registerRequest.getPassword());
		userEntity.setAuthProvider(registerRequest.getAuthProvider());
		return userEntity;
	}
}
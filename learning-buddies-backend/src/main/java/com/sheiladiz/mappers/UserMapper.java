package com.sheiladiz.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.models.UserEntity;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(source = "profile.id", target = "profileId")
	UserDTO userEntityToUserDTO(UserEntity userEntity);

	@Mapping(source = "profileId", target = "profile.id")
	UserEntity userDTOToUserEntity(UserDTO userDTO);

	List<UserDTO> userEntitiesToUserDTOs(List<UserEntity> userEntities);

	List<UserEntity> userDTOsToUserEntities(List<UserDTO> userDTOs);
}

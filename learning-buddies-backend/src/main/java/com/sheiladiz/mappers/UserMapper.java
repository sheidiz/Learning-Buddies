package com.sheiladiz.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.sheiladiz.dtos.user.RequestRegisterDto;
import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.models.User;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "profileId", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "enabled", ignore = true)
	@Mapping(target = "accountNonExpired", ignore = true)
	@Mapping(target = "accountNonLocked", ignore = true)
	@Mapping(target = "credentialsNonExpired", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	User registerUsertoUser(RequestRegisterDto requestRegisterDto);

	ResponseUserDto userToUserDto(User user);

	List<ResponseUserDto> usersToUserDtos(List<User> users);

}
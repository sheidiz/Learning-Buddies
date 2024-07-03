package com.sheiladiz.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;

@Mapper
public interface ProfileMapper {

	ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

	@Mapping(source = "user.id", target = "userId")
	ProfileDTO profileToProfileDTO(Profile profile);

	@Mapping(source = "userId", target = "user.id")
	Profile profileDTOToProfile(ProfileDTO profileDTO);

	@Mapping(target = "user", source = "user")
	void updateProfileFromDTO(ProfileDTO profileDTO, @MappingTarget Profile profile, UserEntity user);

	List<ProfileDTO> profilesToProfileDTOs(List<Profile> profiles);

	List<Profile> profileDTOsToProfiles(List<ProfileDTO> profileDTOs);
}

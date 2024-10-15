package com.sheiladiz.mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sheiladiz.dtos.profile.RequestProfileDto;
import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.dtos.profile.ResponseProtectedProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "skillsLearned", ignore = true)
	@Mapping(target = "skillsToLearn", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Profile requestProfileToProfile(RequestProfileDto requestProfileDto);

	@Mapping(source = "user.id", target = "userId")
	@Mapping(target = "skillsLearned", expression = "java(mapSkills(profile.getSkillsLearned()))")
	@Mapping(target = "skillsToLearn", expression = "java(mapSkills(profile.getSkillsToLearn()))")
	ResponseProtectedProfileDto profileToProtectedProfileDto(Profile profile);

	@Mapping(source = "user.id", target = "userId")
	@Mapping(target = "skillsLearned", expression = "java(mapSkills(profile.getSkillsLearned()))")
	@Mapping(target = "skillsToLearn", expression = "java(mapSkills(profile.getSkillsToLearn()))")
	ResponseProfileDto profileToProfileDto(Profile profile);

	default List<String> mapSkills(Set<Skill> skills) {
		return skills.stream()
				.map(Skill::getName)
				.collect(Collectors.toList());
	}

	List<ResponseProtectedProfileDto> profilesToProtectedProfileDtos(List<Profile> profiles);

	List<ResponseProfileDto> profilesToProfileDtos(List<Profile> profiles);
}
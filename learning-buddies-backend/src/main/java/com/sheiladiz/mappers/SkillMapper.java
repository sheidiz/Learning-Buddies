package com.sheiladiz.mappers;

import com.sheiladiz.dtos.skill.ResponseSkillDto;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.SkillCategory;
import org.mapstruct.Mapper;

import com.sheiladiz.dtos.skill.RequestSkillDto;
import com.sheiladiz.models.Skill;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SkillMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "categories", ignore = true)
	@Mapping(target = "profilesWhoLearnedThisSkill", ignore = true)
	@Mapping(target = "profilesLearningThisSkill", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Skill requestSkillDtoToSkill(RequestSkillDto requestSkillDto);

	@Mapping(target = "categories", expression = "java(mapCategories(skill.getCategories()))")
	@Mapping(target = "profilesWhoLearnedThisSkillIds", expression = "java(mapProfilesIds(skill.getProfilesWhoLearnedThisSkill()))")
	@Mapping(target = "profilesLearningThisSkillIds", expression = "java(mapProfilesIds(skill.getProfilesLearningThisSkill()))")
	ResponseSkillDto skillToResponseSkillDto(Skill skill);

	default List<String> mapCategories(Set<SkillCategory> skills) {
		return skills.stream()
				.map(SkillCategory::getName)
				.collect(Collectors.toList());
	}

	default List<Long> mapProfilesIds(List<Profile> profiles) {
		return profiles.stream()
				.map(Profile::getId)
				.collect(Collectors.toList());
	}

	List<ResponseSkillDto> skillsToResponseSkillDtos(List<Skill> skills);

}
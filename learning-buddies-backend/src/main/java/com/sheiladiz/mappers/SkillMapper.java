package com.sheiladiz.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.models.Skill;

@Mapper
public interface SkillMapper {
	SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);

	@Mapping(source = "categories.id", target = "categoryIds")
	@Mapping(source = "usersWhoLearnedThisSkill.id", target = "usersWhoLearnedThisSkillIds")
	@Mapping(source = "usersLearningThisSkill.id", target = "usersLearningThisSkillIds")
	SkillDTO skillToSkillDTO(Skill skill);

	@Mapping(source = "categoryIds", target = "categories")
	@Mapping(source = "usersWhoLearnedThisSkillIds", target = "usersWhoLearnedThisSkill")
	@Mapping(source = "usersLearningThisSkillIds", target = "usersLearningThisSkill")
	Skill skillDTOToSkill(SkillDTO skillDTO);
}

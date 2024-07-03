package com.sheiladiz.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.models.SkillCategory;

@Mapper
public interface SkillCategoryMapper {
	
	SkillCategoryMapper INSTANCE = Mappers.getMapper(SkillCategoryMapper.class);

	@Mapping(source = "skills.id", target = "skillIds")
	SkillCategoryDTO skillCategoryToSkillCategoryDTO(SkillCategory skillCategory);

	@Mapping(source = "skillIds", target = "skills")
	SkillCategory skillCategoryDTOToSkillCategory(SkillCategoryDTO skillCategoryDTO);
}

package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.models.SkillCategory;

@Component
public class SkillCategoryMapper {

	public SkillCategoryDTO toDTO(SkillCategory category) {
		return SkillCategoryDTO.builder().id(category.getId()).name(category.getName())
				.build();
	}

	public List<SkillCategoryDTO> skillCategoriesToSkillCategoryDTOs(List<SkillCategory> categories) {
		return categories.stream().map(this::toDTO).collect(Collectors.toList());
	}

}
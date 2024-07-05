package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.services.SkillService;

@Component
public class SkillCategoryMapper {

	@Autowired
	private SkillService skillService;

	public SkillCategoryDTO skillCategoryToSkillCategoryDTO(SkillCategory category) {
		return SkillCategoryDTO.builder()
				.id(category.getId())
				.name(category.getName())
				.skillNames(mapSkills(category.getSkills()))
				.build();
	}
 
	public SkillCategory skillCategoryDTOToSkillCategory(SkillCategoryDTO categoryDTO) {
		return SkillCategory.builder()
				.id(categoryDTO.getId())
				.name(categoryDTO.getName())
				.skills(mapSkillsNames(categoryDTO.getSkillNames()))
				.build();
	}

	private List<String> mapSkills(List<Skill> skills) {
		return skills.stream().map(Skill::getName).collect(Collectors.toList());
	}

	private List<Skill> mapSkillsNames(List<String> skills) {
		return skills.stream().map(skillService::findSkillByName).collect(Collectors.toList());
	}

	public List<SkillCategoryDTO> skillCategoriesToSkillCategoryDTOs(List<SkillCategory> categories) {
		return categories.stream().map(this::skillCategoryToSkillCategoryDTO).collect(Collectors.toList());
	}

	public List<SkillCategory> skillCategoryDTOsToSkillCategories(List<SkillCategoryDTO> categories) {
		return categories.stream().map(this::skillCategoryDTOToSkillCategory).collect(Collectors.toList());
	}

}
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
		SkillCategoryDTO categoryDTO = new SkillCategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		categoryDTO.setSkillNames(mapSkills(category.getSkills()));
		return categoryDTO;
	}

	public SkillCategory skillCategoryDTOToSkillCategory(SkillCategoryDTO categoryDTO) {
		SkillCategory category = new SkillCategory();
		category.setId(categoryDTO.getId());
		category.setName(categoryDTO.getName());
		category.setSkills(mapSkillsNames(categoryDTO.getSkillNames()));
		return category;
	}

	private List<String> mapSkills(List<Skill> skills) {
		return skills.stream().map(Skill::getName).collect(Collectors.toList());
	}

	private List<Skill> mapSkillsNames(List<String> skills) {
		return skills.stream().map(skillService::getSkillByName).collect(Collectors.toList());
	}

	public List<SkillCategoryDTO> skillCategoriesToSkillCategoryDTOs(List<SkillCategory> categories) {
		return categories.stream().map(this::skillCategoryToSkillCategoryDTO).collect(Collectors.toList());
	}

	public List<SkillCategory> skillCategoryDTOsToSkillCategories(List<SkillCategoryDTO> categories) {
		return categories.stream().map(this::skillCategoryDTOToSkillCategory).collect(Collectors.toList());
	}

}
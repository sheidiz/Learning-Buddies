package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;

public interface SkillService {
	
	SkillCategoryDTO saveCategory(SkillCategoryDTO newCategory);
	SkillDTO saveSkill(SkillDTO newSkill);
	List<SkillCategoryDTO> allCategories();
	List<SkillDTO> allSkills();
	SkillDTO getSkillById(Long id);
	SkillDTO getSkillByName(String name);
	Skill getSkillEntityById(Long id);
	Skill getSkillEntityByName(String name);
	SkillCategoryDTO getCategoryById(Long id);
	SkillCategoryDTO getCategoryByName(String name);
	SkillCategory getCategoryEntityById(Long id);
	SkillCategory getCategoryEntityByName(String name);
	SkillDTO updateSkill(Long skillId,SkillDTO skillDTO);
	SkillCategoryDTO updateCategory(Long categoryId,SkillCategoryDTO categoryDTO);
	void deleteCategory(Long id);
	void deleteSkill(Long id);
}

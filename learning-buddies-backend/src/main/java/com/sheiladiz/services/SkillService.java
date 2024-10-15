package com.sheiladiz.services;

import java.util.List;
import java.util.Set;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;

public interface SkillService {

	SkillCategory saveCategory(SkillCategoryDTO newCategory);
	List<SkillCategory> allCategories();
	SkillCategory getCategoryById(Long id);
	SkillCategory getCategoryByName(String name);
	SkillCategory updateCategory(Long categoryId, SkillCategoryDTO newCategoryDTO);
	void deleteCategoryById(Long id);
	Skill saveSkill(SkillDTO newSkill);
	List<Skill> allSkills();
	Skill getSkillById(Long id);
	Skill getSkillByName(String name);
	List<Skill> getSkillsFromNames(List<String> names);
	Skill updateSkill(Long skillId, SkillDTO newSkillDTO);
	void deleteSkillById(Long id);
	List<SkillCategory> getCategoriesFromNames(SkillDTO newSkill);
	Set<Skill> getSkillsByNames(List<String> names);
}
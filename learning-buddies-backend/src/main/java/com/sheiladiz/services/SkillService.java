package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;

public interface SkillService {
	
	// CREATE
	SkillCategory saveCategory(SkillCategory newCategory);
	Skill saveSkill(Skill newSkill);
	
	// READ
	List<SkillCategory> allCategories();
	List<Skill> allSkills();
	Skill getSkillById(Long id);
	Skill getSkillByName(String name);
	SkillCategory getCategoryById(Long id);
	SkillCategory getCategoryByName(String name);
	
	// UPDATE
	SkillCategory updateCategory(SkillCategory category);
	Skill updateSkill(Skill skill);
	
	// DELETE
	void deleteCategory(Long id);
	void deleteSkill(Long id);
}

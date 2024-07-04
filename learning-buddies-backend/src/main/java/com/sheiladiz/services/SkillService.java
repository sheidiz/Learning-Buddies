package com.sheiladiz.services;

import java.util.List;

import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;

public interface SkillService {
	
	// CREATE / UPDATE
	SkillCategory saveCategory(SkillCategory newCategory);
	Skill saveSkill(Skill newSkill);
	
	// READ
	List<SkillCategory> allCategories();
	List<Skill> allSkills();
	Skill findSkillById(Long id);
	Skill findSkillByName(String name);
	SkillCategory findCategoryById(Long id);
	SkillCategory findCategoryByName(String name);
	
	// DELETE
	void deleteCategory(Long id);
	void deleteSkill(Long id);
}

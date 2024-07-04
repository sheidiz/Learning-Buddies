package com.sheiladiz.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.repositories.SkillCategoryRepository;
import com.sheiladiz.repositories.SkillRepository;
import com.sheiladiz.services.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private SkillCategoryRepository categoryRepository;

	public SkillCategory saveCategory(SkillCategory newCategory) {
		return categoryRepository.save(newCategory);
	}

	public Skill saveSkill(Skill newSkill) {
		return skillRepository.save(newSkill);
	}

	public List<SkillCategory> allCategories() {
		return categoryRepository.findAll();
	}

	public List<Skill> allSkills() {
		return skillRepository.findAll();
	}

	public Skill findSkillById(Long id) {
		return skillRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Habilidad con id [" + id + "] no encontrada"));
	}

	public Skill findSkillByName(String name) {
		return skillRepository.findByName(name)
				.orElseThrow(() -> new UserNotFoundException("Habilidad [" + name + "] no encontrada"));
	}

	public SkillCategory findCategoryById(Long id) {
		return categoryRepository.findById(id).orElseThrow(
				() -> new UserNotFoundException("Categoria de habilidad con id [" + id + "] no encontrada"));
	}

	public SkillCategory findCategoryByName(String name) {
		return categoryRepository.findByName(name)
				.orElseThrow(() -> new UserNotFoundException("Categoria de habilidad [" + name + "] no encontrada"));
	}

	public void deleteCategory(Long id) {
		SkillCategory category = findCategoryById(id);
		categoryRepository.delete(category);
	}

	public void deleteSkill(Long id) {
		Skill skill = findSkillById(id);
		skillRepository.delete(skill);
	}

}

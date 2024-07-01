package com.sheiladiz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.repositories.SkillCategoryRepository;
import com.sheiladiz.repositories.SkillRepository;

@Service
public class SkillService {

	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private SkillCategoryRepository categoryRepository;

	public SkillCategory saveCategory(SkillCategory newCategory) {
		Optional<SkillCategory> foundCategory = categoryRepository.findByName(newCategory.getName());

		if (foundCategory.isPresent()) {
			return foundCategory.get();
		}

		return categoryRepository.save(newCategory);
	}

	public Skill saveSkill(Skill newSkill) {
		Optional<Skill> foundSkill = skillRepository.findByName(newSkill.getName());

		if (foundSkill.isPresent()) {
			return foundSkill.get();
		}
		return skillRepository.save(newSkill);
	}

	public List<SkillCategory> allCategories() {
		return categoryRepository.findAll();
	}

	public List<Skill> allSkills() {
		return skillRepository.findAll();
	}

	public SkillCategory updateCategory(SkillCategory category) {
		if (categoryRepository.existsById(category.getId())) {
			return categoryRepository.save(category);
		} else {
			throw new IllegalArgumentException("Categoria de habilidad no encontrada");
		}
	}

	public Skill updateSkill(Skill skill) {
		if (skillRepository.existsById(skill.getId())) {
			return skillRepository.save(skill);
		} else {
			throw new IllegalArgumentException("Habilidad no encontrada");
		}
	}

	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

	public void deleteSkill(Long id) {
		skillRepository.deleteById(id);
	}

}

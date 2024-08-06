package com.sheiladiz.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.exceptions.skill.SkillAlreadyCreatedException;
import com.sheiladiz.exceptions.skill.SkillCategoryAlreadyCreatedException;
import com.sheiladiz.exceptions.skill.SkillCategoryNotFoundException;
import com.sheiladiz.exceptions.skill.SkillNotFoundException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.mappers.SkillCategoryMapper;
import com.sheiladiz.mappers.SkillMapper;
import com.sheiladiz.models.Profile;
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

	@Autowired
	private SkillMapper skillMapper;

	@Autowired
	private SkillCategoryMapper categoryMapper;

	public SkillCategoryDTO saveCategory(SkillCategoryDTO newCategory) {
		Optional<SkillCategory> existingCategory = categoryRepository.findByName(newCategory.getName());
		if (existingCategory.isPresent()) {
			throw new SkillCategoryAlreadyCreatedException("Ya existe una categoria de habilidad con ese nombre");
		}

		List<Skill> skills = new ArrayList<>();
		SkillCategory category = SkillCategory.builder().id(newCategory.getId()).name(newCategory.getName())
				.skills(skills).build();

		SkillCategory savedCategory = categoryRepository.save(category);
		return categoryMapper.toDTO(savedCategory);
	}

	public SkillDTO saveSkill(SkillDTO newSkill) {
		Optional<Skill> existingSkill = skillRepository.findByName(newSkill.getName());
		if (existingSkill.isPresent()) {
			throw new SkillAlreadyCreatedException("Ya existe una habilidad con ese nombre");
		}

		List<SkillCategory> categories = new ArrayList<>();
		List<Profile> profiles = new ArrayList<>();
		for (String name : newSkill.getCategories()) {
			SkillCategory category = categoryRepository.findByName(name).orElseThrow(
					() -> new SkillCategoryNotFoundException("Categoria de habilidad [" + name + "] no encontrada"));
			categories.add(category);
		}
		Skill skill = Skill.builder().id(newSkill.getId()).skillType(newSkill.getSkillType()).name(newSkill.getName())
				.categories(categories).profilesLearningThisSkill(profiles).profilesWhoLearnedThisSkill(profiles)
				.build();

		Skill savedSkill = skillRepository.save(skill);
		return skillMapper.toDTO(savedSkill);
	}

	public List<SkillCategoryDTO> allCategories() {
		return categoryMapper.skillCategoriesToSkillCategoryDTOs(categoryRepository.findAll());
	}

	public List<SkillDTO> allSkills() {
		return skillMapper.skillsToSkillDTOs(skillRepository.findAll());
	}

	public SkillDTO getSkillById(Long id) {
		Skill skill = skillRepository.findById(id)
				.orElseThrow(() -> new SkillNotFoundException("Habilidad con id [" + id + "] no encontrada"));
		return skillMapper.toDTO(skill);
	}

	public SkillDTO getSkillByName(String name) {
		Skill skill = skillRepository.findByName(name)
				.orElseThrow(() -> new SkillNotFoundException("Habilidad [" + name + "] no encontrada"));
		return skillMapper.toDTO(skill);
	}

	public Skill getSkillEntityById(Long id) {
		return skillRepository.findById(id)
				.orElseThrow(() -> new SkillNotFoundException("Habilidad con id [" + id + "] no encontrada"));
	}

	public Skill getSkillEntityByName(String name) {
		return skillRepository.findByName(name)
				.orElseThrow(() -> new SkillNotFoundException("Habilidad [" + name + "] no encontrada"));
	}

	public SkillCategoryDTO getCategoryById(Long id) {
		SkillCategory category = categoryRepository.findById(id).orElseThrow(
				() -> new SkillCategoryNotFoundException("Categoria de habilidad con id [" + id + "] no encontrada"));
		return categoryMapper.toDTO(category);
	}

	public SkillCategoryDTO getCategoryByName(String name) {
		SkillCategory category = categoryRepository.findByName(name)
				.orElseThrow(() -> new SkillCategoryNotFoundException("Categoria de habilidad [" + name + "] no encontrada"));
		return categoryMapper.toDTO(category);
	}

	public SkillCategory getCategoryEntityById(Long id) {
		return categoryRepository.findById(id).orElseThrow(
				() -> new SkillCategoryNotFoundException("Categoria de habilidad con id [" + id + "] no encontrada"));
	}

	public SkillCategory getCategoryEntityByName(String name) {
		return categoryRepository.findByName(name)
				.orElseThrow(() -> new SkillCategoryNotFoundException("Categoria de habilidad [" + name + "] no encontrada"));
	}

	public void deleteCategory(Long id) {
		SkillCategory category = categoryRepository.findById(id).orElseThrow(
				() -> new SkillCategoryNotFoundException("Categoria de habilidad con id [" + id + "] no encontrada"));
		categoryRepository.delete(category);
	}

	public void deleteSkill(Long id) {
		Skill skill = skillRepository.findById(id)
				.orElseThrow(() -> new SkillNotFoundException("Habilidad con id [" + id + "] no encontrada"));
		skillRepository.delete(skill);
	}

	public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
		Skill existingSkill = skillRepository.findById(id)
				.orElseThrow(() -> new SkillNotFoundException("Habilidad con id [" + id + "] no encontrada"));

		if (skillRepository.findByName(skillDTO.getName()).isPresent()) {
			throw new SkillAlreadyCreatedException("Ya existe una habilidad con ese nombre");
		}

		existingSkill.setName(skillDTO.getName());
		if (skillDTO.getSkillType() != null) {
			existingSkill.setSkillType(skillDTO.getSkillType());
		}

		Skill updatedSkill = skillRepository.save(existingSkill);

		return skillMapper.toDTO(updatedSkill);
	}

	public SkillCategoryDTO updateCategory(Long id, SkillCategoryDTO categoryDTO) {
		SkillCategory existingCategory = categoryRepository.findById(id).orElseThrow(
				() -> new SkillCategoryNotFoundException("Categoria de habilidad con id [" + id + "] no encontrada"));

		if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
			throw new SkillCategoryAlreadyCreatedException("Ya existe una categoria creada con ese nombre");
		}

		existingCategory.setName(categoryDTO.getName());

		SkillCategory updatedCategory = categoryRepository.save(existingCategory);
		return categoryMapper.toDTO(updatedCategory);
	}

}

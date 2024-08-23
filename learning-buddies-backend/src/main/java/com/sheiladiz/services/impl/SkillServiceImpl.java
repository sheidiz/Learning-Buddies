package com.sheiladiz.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.mappers.SkillCategoryMapper;
import com.sheiladiz.mappers.SkillMapper;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.repositories.SkillCategoryRepository;
import com.sheiladiz.repositories.SkillRepository;
import com.sheiladiz.services.SkillService;

@Service
public class SkillServiceImpl implements SkillService {
    private final SkillRepository skillRepository;
    private final SkillCategoryRepository categoryRepository;
    private final SkillMapper skillMapper;
    private final SkillCategoryMapper categoryMapper;

    public SkillServiceImpl(SkillRepository skillRepository, SkillCategoryRepository categoryRepository, SkillMapper skillMapper, SkillCategoryMapper categoryMapper) {
        this.skillRepository = skillRepository;
        this.categoryRepository = categoryRepository;
        this.skillMapper = skillMapper;
        this.categoryMapper = categoryMapper;
    }

    public SkillCategory saveCategory(SkillCategoryDTO newCategory) {
        Optional<SkillCategory> existingCategory = categoryRepository.findByName(newCategory.getName());
        if (existingCategory.isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una categoria de habilidad con ese nombre");
        }

        List<Skill> skills = new ArrayList<>();
        SkillCategory category = SkillCategory
                .builder()
                .id(newCategory.getId())
                .name(newCategory.getName())
                .skills(skills).build();

        return categoryRepository.save(category);
    }

    public List<SkillCategory> allCategories() {
        return categoryRepository.findAll();
    }

    public SkillCategory getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoría de habilidad con id [" + id + "] no encontrada"));
    }

    public SkillCategory getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría de habilidad [" + name + "] no encontrada"));
    }

    public SkillCategory updateCategory(Long id, SkillCategoryDTO newCategoryDTO) {
        SkillCategory existingCategory = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoría de habilidad con id [" + id + "] no encontrada"));

        if (categoryRepository.findByName(newCategoryDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una categoría creada con ese nombre");
        }

        existingCategory.setName(newCategoryDTO.getName());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategoryById(Long id) {
        SkillCategory category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoría de habilidad con id [" + id + "] no encontrada"));
        categoryRepository.delete(category);
    }

    public Skill saveSkill(SkillDTO newSkill) {
        Optional<Skill> existingSkill = skillRepository.findByName(newSkill.getName());
        if (existingSkill.isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una habilidad con ese nombre");
        }

        List<SkillCategory> categories = new ArrayList<>();
        for (String name : newSkill.getCategories()) {
            SkillCategory category = categoryRepository.findByName(name).orElseThrow(
                    () -> new ResourceNotFoundException("Categoria de habilidad [" + name + "] no encontrada"));
            categories.add(category);
        }
        Skill skill = Skill.builder()
                .id(newSkill.getId())
                .skillType(newSkill.getSkillType())
                .name(newSkill.getName())
                .categories(categories)
                .profilesLearningThisSkill(new ArrayList<>())
                .profilesLearningThisSkill(new ArrayList<>())
                .build();

        return skillRepository.save(skill);
    }

    public List<Skill> allSkills() {
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad con id [" + id + "] no encontrada"));
    }

    public Skill getSkillByName(String name) {
        return skillRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad [" + name + "] no encontrada"));
    }

    public Skill updateSkill(Long id, SkillDTO newSkillDTO) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad con id [" + id + "] no encontrada"));

        if (skillRepository.findByName(newSkillDTO.getName()).isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una habilidad con ese nombre");
        }

        existingSkill.setName(newSkillDTO.getName());
        if (newSkillDTO.getSkillType() != null) {
            existingSkill.setSkillType(newSkillDTO.getSkillType());
        }

        return skillRepository.save(existingSkill);
    }

    public void deleteSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad con id [" + id + "] no encontrada"));
        skillRepository.delete(skill);
    }

}

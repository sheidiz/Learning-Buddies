package com.sheiladiz.services.impl;

import com.sheiladiz.dtos.skill.RequestSkillCategoryDto;
import com.sheiladiz.dtos.skill.ResponseSkillCategoryDto;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.mappers.SkillCategoryMapper;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.repositories.SkillCategoryRepository;
import com.sheiladiz.services.SkillCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkillCategoryServiceImpl implements SkillCategoryService {

    private final SkillCategoryRepository categoryRepository;
    private final SkillCategoryMapper categoryMapper;

    public ResponseSkillCategoryDto saveCategory(RequestSkillCategoryDto newCategory) {
        Optional<SkillCategory> existingCategory = categoryRepository.findByName(newCategory.name());
        if (existingCategory.isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una categoría de habilidad con ese nombre");
        }

        SkillCategory skillCategory = categoryMapper.requestCategoryDtoToCategory(newCategory);

        SkillCategory savedCategory = categoryRepository.save(skillCategory);

        return categoryMapper.categoryToResponseCategoryDto(savedCategory);
    }

    public List<ResponseSkillCategoryDto> allCategories() {
        List<SkillCategory> categories = categoryRepository.findAll();
        return categoryMapper.categoriesToSkillCategoryDtos(categories);
    }

    public ResponseSkillCategoryDto getCategoryById(Long id) {
        SkillCategory category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoría de habilidad con id [" + id + "] no encontrada"));
        return categoryMapper.categoryToResponseCategoryDto(category);
    }

    public ResponseSkillCategoryDto getCategoryByName(String name) {
        SkillCategory category = categoryRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría de habilidad [" + name + "] no encontrada"));
        return categoryMapper.categoryToResponseCategoryDto(category);
    }

    public ResponseSkillCategoryDto updateCategory(Long id, RequestSkillCategoryDto newCategoryDTO) {
        SkillCategory existingCategory = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoría de habilidad con id [" + id + "] no encontrada"));

        if (categoryRepository.findByName(newCategoryDTO.name()).isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una categoría creada con ese nombre");
        }

        existingCategory.setName(newCategoryDTO.name());

        SkillCategory updatedCategory = categoryRepository.save(existingCategory);

        return categoryMapper.categoryToResponseCategoryDto(updatedCategory);
    }

    public void deleteCategoryById(Long id) {
        SkillCategory category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Categoría de habilidad con id [" + id + "] no encontrada"));
        categoryRepository.delete(category);
    }

}

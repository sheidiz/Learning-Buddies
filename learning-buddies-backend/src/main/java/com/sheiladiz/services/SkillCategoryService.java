package com.sheiladiz.services;

import com.sheiladiz.dtos.skill.RequestSkillCategoryDto;
import com.sheiladiz.dtos.skill.ResponseSkillCategoryDto;

import java.util.List;

public interface SkillCategoryService {

    ResponseSkillCategoryDto saveCategory(RequestSkillCategoryDto newCategory);

    List<ResponseSkillCategoryDto> allCategories();

    ResponseSkillCategoryDto getCategoryById(Long id);

    ResponseSkillCategoryDto getCategoryByName(String name);

    ResponseSkillCategoryDto updateCategory(Long categoryId, RequestSkillCategoryDto newCategoryDTO);

    void deleteCategoryById(Long id);

}

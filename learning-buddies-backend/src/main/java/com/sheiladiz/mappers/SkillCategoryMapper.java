package com.sheiladiz.mappers;

import java.util.List;

import com.sheiladiz.dtos.skill.ResponseSkillCategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sheiladiz.dtos.skill.RequestSkillCategoryDto;
import com.sheiladiz.models.SkillCategory;

@Mapper(componentModel = "spring")
public interface SkillCategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SkillCategory requestCategoryDtoToCategory(RequestSkillCategoryDto category);

    ResponseSkillCategoryDto categoryToResponseCategoryDto(SkillCategory category);

    List<ResponseSkillCategoryDto> categoriesToSkillCategoryDtos(List<SkillCategory> categories);

}
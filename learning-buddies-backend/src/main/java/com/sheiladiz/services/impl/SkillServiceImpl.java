package com.sheiladiz.services.impl;

import java.util.*;

import com.sheiladiz.dtos.skill.ResponseSkillDto;
import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.mappers.SkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.skill.RequestSkillDto;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.repositories.SkillCategoryRepository;
import com.sheiladiz.repositories.SkillRepository;
import com.sheiladiz.services.SkillService;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final SkillCategoryRepository categoryRepository;
    private final SkillMapper skillMapper;

    public ResponseSkillDto saveSkill(RequestSkillDto requestSkillDto) {
        Optional<Skill> existingSkill = skillRepository.findByName(requestSkillDto.name());
        if (existingSkill.isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una habilidad con ese nombre");
        }

        Set<SkillCategory> categories = getCategoriesFromNames(requestSkillDto);
        Skill skill = skillMapper.requestSkillDtoToSkill(requestSkillDto);
        skill.setCategories(categories);
        skill.setProfilesWhoLearnedThisSkill(new ArrayList<>());
        skill.setProfilesLearningThisSkill(new ArrayList<>());

        Skill savedSkill = skillRepository.save(skill);

        return skillMapper.skillToResponseSkillDto(savedSkill);
    }

    public List<ResponseSkillDto> allSkills() {
        List<Skill> skills = skillRepository.findAll();

        return skillMapper.skillsToResponseSkillDtos(skills);
    }

    public ResponseSkillDto getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad con id [" + id + "] no encontrada"));
        return skillMapper.skillToResponseSkillDto(skill);
    }

    public ResponseSkillDto getSkillByName(String name) {
        Skill skill = skillRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad [" + name + "] no encontrada"));
        return skillMapper.skillToResponseSkillDto(skill);
    }

    public ResponseSkillDto updateSkill(Long id, RequestSkillDto requestSkillDto) {
        Skill existingSkill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad con id [" + id + "] no encontrada"));

        if (skillRepository.findByName(requestSkillDto.name()).isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una habilidad con ese nombre");
        }

        existingSkill.setName(requestSkillDto.name());
        if (requestSkillDto.skillType() != null) {
            existingSkill.setSkillType(requestSkillDto.skillType());
        }

        Skill updatedSkill = skillRepository.save(existingSkill);

        return skillMapper.skillToResponseSkillDto(updatedSkill);
    }

    public void deleteSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Habilidad con id [" + id + "] no encontrada"));
        skillRepository.delete(skill);
    }

    private Set<SkillCategory> getCategoriesFromNames(RequestSkillDto newSkill) {
        Set<SkillCategory> categories = new HashSet<>();

        if (newSkill.categories() == null) {
            return categories;
        }

        for (String name : newSkill.categories()) {
            SkillCategory category = categoryRepository.findByName(name).orElseThrow(
                    () -> new ResourceNotFoundException("Categoría de habilidad [" + name + "] no encontrada"));
            categories.add(category);
        }
        return categories;
    }

}

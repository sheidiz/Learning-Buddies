package com.sheiladiz.services;
/*
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.repositories.SkillCategoryRepository;
import com.sheiladiz.repositories.SkillRepository;
import com.sheiladiz.services.impl.SkillServiceImpl;

@ExtendWith(MockitoExtension.class)
public class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private SkillCategoryRepository categoryRepository;

    @InjectMocks
    private SkillServiceImpl skillService;

    private SkillCategory category;
    private SkillCategoryDTO categoryDTO;
    private Skill skill;
    private SkillDTO skillDTO;

    @BeforeEach
    public void setup() {
        category = SkillCategory.builder()
                .id(1L)
                .name("FrontEnd")
                .build();

        categoryDTO = SkillCategoryDTO.builder()
                .id(1L)
                .name("FrontEnd")
                .build();

        skill = Skill.builder()
                .id(1L)
                .name("HTML")
                .skillType("Desarrollo web")
                .categories(List.of(category))
                .build();

        skillDTO = SkillDTO.builder()
                .id(1L)
                .name("HTML")
                .skillType("Desarrollo web")
                .categories(List.of("FrontEnd"))
                .build();
    }

    @Test
    void shouldSaveCategory_whenCategoryIsValid() {
        when(categoryRepository.findByName(categoryDTO.getName())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(SkillCategory.class))).thenReturn(category);

        SkillCategory result = skillService.saveCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(categoryDTO.getName(), result.getName());
        verify(categoryRepository).findByName(categoryDTO.getName());
        verify(categoryRepository).save(any(SkillCategory.class));
    }

    @Test
    void shouldThrowResourceAlreadyExistsException_whenCategoryNameAlreadyExists() {
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        assertThrows(ResourceAlreadyExistsException.class, () -> skillService.saveCategory(categoryDTO));

        verify(categoryRepository, times(1)).findByName(category.getName());
        verify(categoryRepository, never()).save(any(SkillCategory.class));
    }

    @Test
    public void shouldReturnCategoryById_whenCategoryIdIsValid() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        SkillCategory result = skillService.getCategoryById(category.getId());

        assertEquals(category, result);
    }

    @Test
    public void shouldThrowResourceNotFoundException_whenCategoryIdIsInvalid() {
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> skillService.getCategoryById(category.getId()));
    }

    @Test
    public void shouldReturnCategoryByName_whenCategoryNameIsValid() {
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        SkillCategory result = skillService.getCategoryByName(category.getName());

        assertEquals(category, result);
    }

    @Test
    public void shouldThrowResourceNotFoundException_whenCategoryNameIsInvalid() {
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> skillService.getCategoryByName(category.getName()));
    }

    @Test
    public void shouldUpdateCategory_whenCategoryIsValid() {
        SkillCategory existingCategory = new SkillCategory();
        existingCategory.setId(1L);
        existingCategory.setName("Fronten");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(SkillCategory.class))).thenReturn(category);

        SkillCategory result = skillService.updateCategory(1L, categoryDTO);

        assertEquals(category.getName(), result.getName());
        verify(categoryRepository).save(any(SkillCategory.class));
    }

    @Test
    public void shouldDeleteCategoryById_whenCategoryIdIsValid() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        skillService.deleteCategoryById(1L);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void shouldThrowResourceAlreadyExistsException_whenSkillNameAlreadyExists() {
        when(skillRepository.findByName(skill.getName())).thenReturn(Optional.of(skill));

        assertThrows(ResourceAlreadyExistsException.class, () -> skillService.saveSkill(skillDTO));

        verify(skillRepository).findByName(skill.getName());
        verifyNoMoreInteractions(skillRepository, categoryRepository);
    }

    @Test
    public void shouldSaveSkill_whenSkillIsValid() {
        when(skillRepository.findByName(skillDTO.getName())).thenReturn(Optional.empty());
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);

        Skill result = skillService.saveSkill(skillDTO);

        assertEquals(skill.getName(), result.getName());
        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    void shouldThrowResourceNotFoundException_whenSavingSkillWithInvalidCategory() {
        when(skillRepository.findByName(skillDTO.getName())).thenReturn(Optional.empty());
        when(categoryRepository.findByName("FrontEnd")).thenReturn(Optional.of(category));
        when(categoryRepository.findByName("BackEnd")).thenReturn(Optional.empty());

        skillDTO.setCategories(List.of("FrontEnd", "BackEnd"));

        assertThrows(ResourceNotFoundException.class, () -> skillService.saveSkill(skillDTO));

        verify(skillRepository).findByName(skillDTO.getName());
        verify(categoryRepository).findByName("FrontEnd");
        verify(categoryRepository).findByName("BackEnd");
        verify(skillRepository, never()).save(any(Skill.class));
    }

    @Test
    public void shouldReturnSkillById_whenSkillIdIsValid() {
        when(skillRepository.findById(skill.getId())).thenReturn(Optional.of(skill));

        Skill result = skillService.getSkillById(skill.getId());

        assertEquals(skill, result);
    }

    @Test
    public void shouldThrowResourceNotFoundException_whenSkillIdIsInvalid() {
        when(skillRepository.findById(skill.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> skillService.getSkillById(skill.getId()));
    }

    @Test
    public void shouldReturnSkillByName_whenSkillNameIsValid() {
        when(skillRepository.findByName(skill.getName())).thenReturn(Optional.of(skill));

        Skill result = skillService.getSkillByName(skill.getName());

        assertEquals(skill, result);
    }

    @Test
    public void shouldThrowResourceNotFoundException_whenSkillNameIsInvalid() {
        when(skillRepository.findByName(skill.getName())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> skillService.getSkillByName(skill.getName()));
    }

    @Test
    public void shouldUpdateSkill_whenSkillIsValid() {
        Skill existingSkill = new Skill();
        existingSkill.setId(1L);
        existingSkill.setName("Fronten");

        when(skillRepository.findById(anyLong())).thenReturn(Optional.of(existingSkill));
        when(skillRepository.save(any(Skill.class))).thenReturn(skill);

        Skill result = skillService.updateSkill(1L, skillDTO);

        assertEquals(skill.getName(), result.getName());
        verify(skillRepository).save(any(Skill.class));
    }

    @Test
    public void shouldDeleteSkillById_whenSkillIdIsValid() {
        when(skillRepository.findById(anyLong())).thenReturn(Optional.of(skill));

        skillService.deleteSkillById(1L);

        verify(skillRepository, times(1)).delete(skill);
    }
}*/

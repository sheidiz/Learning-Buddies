package com.sheiladiz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.exceptions.skill.SkillAlreadyCreatedException;
import com.sheiladiz.exceptions.skill.SkillCategoryAlreadyCreatedException;
import com.sheiladiz.exceptions.skill.SkillCategoryNotFoundException;
import com.sheiladiz.exceptions.skill.SkillNotFoundException;
import com.sheiladiz.mappers.SkillCategoryMapper;
import com.sheiladiz.mappers.SkillMapper;
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

	@Mock
	private SkillMapper skillMapper;

	@Mock
	private SkillCategoryMapper categoryMapper;

	@InjectMocks
	private SkillServiceImpl skillService;

	private Skill skill;
	private SkillDTO skillDTO;
	private SkillCategory category;
	private SkillCategoryDTO categoryDTO;

	@BeforeEach
	public void setup() {
		category = new SkillCategory();
		category.setId(1L);
		category.setName("FrontEnd");

		categoryDTO = new SkillCategoryDTO();
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());

		skill = new Skill();
		skill.setId(1L);
		skill.setName("HTML");
		skill.setSkillType("Desarrollo web");
		skill.setCategories(List.of(category));

		skillDTO = new SkillDTO();
		skillDTO.setId(skill.getId());
		skillDTO.setName(skill.getName());
		skillDTO.setSkillType(skill.getSkillType());
		skillDTO.setCategories(skillMapper.mapCategories(skill.getCategories()));
	}

	@Test
	public void whenSaveExistingCategory_thenThrowException() {
	    when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));
	    
	    assertThrows(SkillCategoryAlreadyCreatedException.class, () -> {
	        skillService.saveCategory(categoryDTO);
	    });
	}

	@Test
	public void whenSaveCategory_thenReturnCategoryDTO() {
		when(categoryRepository.findByName(categoryDTO.getName())).thenReturn(Optional.empty());
		when(categoryRepository.save(any(SkillCategory.class))).thenReturn(category);
		when(categoryMapper.toDTO(any(SkillCategory.class))).thenReturn(categoryDTO);
		
		SkillCategoryDTO result = skillService.saveCategory(categoryDTO);
		
		assertEquals(categoryDTO, result);
		
	}

	@Test
	public void whenGetCategoryById_thenReturnCategoryDTO() {
		when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
		when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);
		
		SkillCategoryDTO result = skillService.getCategoryById(category.getId());
		
		assertEquals(categoryDTO, result);
	}

	@Test
	public void whenCategoryNotFoundById_thenThrowException() {
		when(categoryRepository.findById(category.getId())).thenReturn(Optional.empty());
		
		assertThrows(SkillCategoryNotFoundException.class, () -> {
			skillService.getCategoryById(category.getId());
		});
	}

	@Test
	public void whenGetCategoryByName_thenReturnCategoryDTO() {
		when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));
		when(categoryMapper.toDTO(category)).thenReturn(categoryDTO);
		
		SkillCategoryDTO result = skillService.getCategoryByName(category.getName());
		
		assertEquals(categoryDTO, result);
	}

	@Test
	public void whenCategoryNotFoundByName_thenThrowException() {
		when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());
		
		assertThrows(SkillCategoryNotFoundException.class, () -> {
			skillService.getCategoryByName(category.getName());
		});
	}

	@Test
	public void whenUpdateCategory_thenReturnUpdatedCategoryDTO() {
		SkillCategory existingCategory = new SkillCategory();
		existingCategory.setId(1L);
		existingCategory.setName("Fronten");

		SkillCategory updatedCategory = new SkillCategory();
		updatedCategory.setId(1L);
		updatedCategory.setName("Frontend");

		when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(existingCategory));
		when(categoryRepository.save(any(SkillCategory.class))).thenReturn(updatedCategory);
		when(categoryMapper.toDTO(updatedCategory)).thenReturn(categoryDTO);

		SkillCategoryDTO result = skillService.updateCategory(1L, categoryDTO);

		assertEquals(categoryDTO, result);
	}

	@Test
	public void whenDeleteCategory_thenVoid() {
		when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
		
		skillService.deleteCategory(1L);
	}

	@Test
	public void whenSaveExistingSkill_thenThrowException() {
	    when(skillRepository.findByName(skill.getName())).thenReturn(Optional.of(skill));
	    
	    assertThrows(SkillAlreadyCreatedException.class, () -> {
	        skillService.saveSkill(skillDTO);
	    });
	}

	@Test
	public void whenSaveSkill_thenReturnSkillDTO() {
		when(skillRepository.findByName(skillDTO.getName())).thenReturn(Optional.empty());
		when(skillRepository.save(any(Skill.class))).thenReturn(skill);
		when(skillMapper.toDTO(any(Skill.class))).thenReturn(skillDTO);
		
		SkillDTO result = skillService.saveSkill(skillDTO);
		
		assertEquals(skillDTO, result);
		
	}

	@Test
	public void whenGetSkillById_thenReturnSkillDTO() {
		when(skillRepository.findById(skill.getId())).thenReturn(Optional.of(skill));
		when(skillMapper.toDTO(skill)).thenReturn(skillDTO);
		
		SkillDTO result = skillService.getSkillById(skill.getId());
		
		assertEquals(skillDTO, result);
	}

	@Test
	public void whenSkillNotFoundById_thenThrowException() {
		when(skillRepository.findById(skill.getId())).thenReturn(Optional.empty());
		
		assertThrows(SkillNotFoundException.class, () -> {
			skillService.getSkillById(skill.getId());
		});
	}

	@Test
	public void whenGetSkillByName_thenReturnSkillDTO() {
		when(skillRepository.findByName(skill.getName())).thenReturn(Optional.of(skill));
		when(skillMapper.toDTO(skill)).thenReturn(skillDTO);
		
		SkillDTO result = skillService.getSkillByName(skill.getName());
		
		assertEquals(skillDTO, result);
	}

	@Test
	public void whenSkillNotFoundByName_thenThrowException() {
		when(skillRepository.findByName(skill.getName())).thenReturn(Optional.empty());
		
		assertThrows(SkillNotFoundException.class, () -> {
			skillService.getSkillByName(skill.getName());
		});
	}

	@Test
	public void whenUpdateSkill_thenReturnUpdatedSkillDTO() {
		Skill existingSkill = new Skill();
		existingSkill.setId(1L);
		existingSkill.setName("Fronten");
		existingSkill.setCategories(List.of(category));

		Skill updatedSkill = new Skill();
		updatedSkill.setId(1L);
		updatedSkill.setName("Frontend");

		when(skillRepository.findById(anyLong())).thenReturn(Optional.of(existingSkill));
		when(skillRepository.save(any(Skill.class))).thenReturn(updatedSkill);
		when(skillMapper.toDTO(updatedSkill)).thenReturn(skillDTO);

		SkillDTO result = skillService.updateSkill(1L, skillDTO);

		assertEquals(skillDTO, result);
	}

	@Test
	public void whenDeleteSkill_thenVoid() {
		when(skillRepository.findById(anyLong())).thenReturn(Optional.of(skill));
		
		skillService.deleteSkill(1L);
	}
}

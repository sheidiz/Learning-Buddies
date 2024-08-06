package com.sheiladiz.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.exceptions.skill.SkillCategoryNotFoundException;
import com.sheiladiz.exceptions.skill.SkillNotFoundException;
import com.sheiladiz.mappers.SkillCategoryMapper;
import com.sheiladiz.mappers.SkillMapper;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.services.impl.SkillServiceImpl;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SkillController.class)
public class SkillControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private SkillMapper skillMapper;

	@Mock
	private SkillCategoryMapper categoryMapper;

	private Skill skill;
	private SkillDTO skillDTO;
	private SkillCategory category;
	private SkillCategoryDTO categoryDTO;

	@MockBean
	private SkillServiceImpl skillService;

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
	public void testCreateCategory_ShouldReturnCreatedCategoryDTO() throws Exception {
		when(skillService.getCategoryEntityByName(anyString())).thenReturn(category);
		when(skillService.saveCategory(any(SkillCategoryDTO.class))).thenReturn(categoryDTO);
		
		mockMvc.perform(post("/api/v1/skills/categories")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(categoryDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(categoryDTO.getName()));	
	}

	@Test
	public void testGetCategoryById_ShouldReturnCategoryDTO() throws Exception {
		when(skillService.getCategoryById(anyLong())).thenReturn(categoryDTO);
		
		mockMvc.perform(get("/api/v1/skills/categories/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(categoryDTO.getName()));
	}

	@Test
	public void testGetCategoryById_ShouldReturnNotFoundWhenCategoryNotFound() throws Exception {
		when(skillService.getCategoryById(anyLong())).thenThrow(new SkillCategoryNotFoundException("Category not found"));
		
		mockMvc.perform(get("/api/v1/skills/categories/1"))
		.andExpect(status().isNotFound())
		.andExpect(content().string("Category not found"));
	}

	@Test
	public void testGetCategories_ShouldReturnListOfCategoryDTOs() throws Exception {
		when(skillService.allCategories()).thenReturn(List.of(categoryDTO));
		
		mockMvc.perform(get("/api/v1/skills/categories"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name").value(categoryDTO.getName()));
	}

	@Test
	public void testUpdateCategory_ShouldReturnUpdatedCategoryDTO() throws Exception {
		when(skillService.updateCategory(anyLong(), any(SkillCategoryDTO.class))).thenReturn(categoryDTO);
		
		mockMvc.perform(put("/api/v1/skills/categories/1")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(categoryDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(categoryDTO.getName()));
	}

	@Test
	public void testDeleteCategoryById_ShouldReturnSuccessMessage() throws Exception {
		doNothing().when(skillService).deleteCategory(anyLong());

		mockMvc.perform(delete("/api/v1/skills/categories/1")).andExpect(status().isOk())
				.andExpect(content().string("Categoria de habilidad eliminada exitosamente"));
	}

	@Test
	public void testCreateSkill_ShouldReturnCreatedSkillDTO() throws Exception {
		when(skillService.getSkillEntityByName(anyString())).thenReturn(skill);
		when(skillService.saveSkill(any(SkillDTO.class))).thenReturn(skillDTO);
		
		mockMvc.perform(post("/api/v1/skills")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(skillDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value(skillDTO.getName()));	
	}

	@Test
	public void testCreateSkill_ShouldReturnNotFoundWhenCategoryNotFound() throws Exception {
		when(skillService.saveSkill(any())).thenThrow(new SkillCategoryNotFoundException("Category not found"));
		
		mockMvc.perform(post("/api/v1/skills")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(skillDTO)))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Category not found"));
	}

	@Test
	public void testGetSkillById_ShouldReturnSkillDTO() throws Exception {
		when(skillService.getSkillById(anyLong())).thenReturn(skillDTO);
		
		mockMvc.perform(get("/api/v1/skills/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(skillDTO.getName()));
	}

	@Test
	public void testGetSkillById_ShouldReturnNotFoundWhenSkillNotFound() throws Exception {
		when(skillService.getSkillById(anyLong())).thenThrow(new SkillNotFoundException("Skill not found"));
		
		mockMvc.perform(get("/api/v1/skills/1"))
		.andExpect(status().isNotFound())
		.andExpect(content().string("Skill not found"));
	}

	@Test
	public void testGetSkills_ShouldReturnListOfSkillDTOs() throws Exception {
		when(skillService.allSkills()).thenReturn(List.of(skillDTO));
		
		mockMvc.perform(get("/api/v1/skills"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name").value(skillDTO.getName()));
	}

	@Test
	public void testUpdateSkill_ShouldReturnUpdatedSkillDTO() throws Exception {
		when(skillService.updateSkill(anyLong(), any(SkillDTO.class))).thenReturn(skillDTO);
		
		mockMvc.perform(put("/api/v1/skills/1")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(skillDTO)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value(skillDTO.getName()));
	}

	@Test
	public void testDeleteSkillById_ShouldReturnSuccessMessage() throws Exception {
		doNothing().when(skillService).deleteSkill(anyLong());

		mockMvc.perform(delete("/api/v1/skills/1")).andExpect(status().isOk())
				.andExpect(content().string("Habilidad eliminada exitosamente"));
	}

}

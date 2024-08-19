package com.sheiladiz.controllers;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.exceptions.skill.SkillAlreadyCreatedException;
import com.sheiladiz.exceptions.skill.SkillCategoryAlreadyCreatedException;
import com.sheiladiz.exceptions.skill.SkillCategoryNotFoundException;
import com.sheiladiz.exceptions.skill.SkillNotFoundException;
import com.sheiladiz.services.SkillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

	@Autowired
	private SkillService skillService;

	@ApiResponses({
			@ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = SkillDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Missing data on request.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Skill category not found.",
					content = @Content),
			@ApiResponse(responseCode = "409", description = "Skill already created.",
					content = @Content)})
	@PostMapping
	public ResponseEntity<?> createSkill(@Valid @RequestBody SkillDTO skillDTO, BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			SkillDTO savedSkillDTO = skillService.saveSkill(skillDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedSkillDTO);
		} catch (SkillAlreadyCreatedException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		} catch (SkillCategoryNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<SkillDTO>> getSkills() {
		List<SkillDTO> skillDTOs = skillService.allSkills();
		return ResponseEntity.ok(skillDTOs);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = SkillDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Skill category not found.",
					content = @Content)})
	@GetMapping("/{skillId}")
	public ResponseEntity<?> getSkillById(@PathVariable("skillId") Long id) {
		try {
			SkillDTO skillDTO = skillService.getSkillById(id);
			return ResponseEntity.ok(skillDTO);
		} catch (SkillNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = SkillDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Skill not found.",
					content = @Content)})
	@PutMapping("/{id}")
	public ResponseEntity<?> updateSkill(@PathVariable("id") Long id, @RequestBody SkillDTO skillDTO) {
		try {
			SkillDTO updateSkillDTO = skillService.updateSkill(id, skillDTO);
			return ResponseEntity.ok(updateSkillDTO);
		} catch (SkillNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Skill deleted.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Skill not found.",
					content = @Content)})
	@DeleteMapping("/{skillId}")
	public ResponseEntity<?> deleteSkillById(@PathVariable("skillId") Long id) {
		try {
			skillService.deleteSkill(id);
			return ResponseEntity.ok("Habilidad eliminada exitosamente");
		} catch (SkillNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = SkillCategoryDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Missing data on request.",
					content = @Content),
			@ApiResponse(responseCode = "409", description = "Skill category already created.",
					content = @Content)})
	@PostMapping("/categories")
	public ResponseEntity<?> createCategory(@Valid @RequestBody SkillCategoryDTO skillCategoryDTO,
			BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}
		try {
			SkillCategoryDTO savedCategory = skillService.saveCategory(skillCategoryDTO);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
		} catch (SkillCategoryAlreadyCreatedException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@GetMapping("/categories")
	public ResponseEntity<List<SkillCategoryDTO>> getCategories() {
		List<SkillCategoryDTO> categoriesDTOs = skillService.allCategories();
		return ResponseEntity.ok(categoriesDTOs);
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = SkillCategoryDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Skill category not found.",
					content = @Content)})
	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Long id) {
		try {
			SkillCategoryDTO categoryDTO = skillService.getCategoryById(id);
			return ResponseEntity.ok(categoryDTO);
		} catch (SkillCategoryNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = SkillCategoryDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Skill category not found.",
					content = @Content)})
	@PutMapping("/categories/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,
			@RequestBody SkillCategoryDTO skillCategoryDTO) {
		try {
			SkillCategoryDTO updatedCategoryDTO = skillService.updateCategory(id, skillCategoryDTO);
			return ResponseEntity.ok(updatedCategoryDTO);
		} catch (SkillCategoryNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Skill category deleted.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Skill category not found.",
					content = @Content)})
	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable("categoryId") Long id) {
		try {
			skillService.deleteCategory(id);
			return ResponseEntity.ok("Categoria de habilidad eliminada exitosamente");
		} catch (SkillCategoryAlreadyCreatedException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}

package com.sheiladiz.controllers;

import com.sheiladiz.dtos.SkillCategoryDTO;
import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.mappers.SkillCategoryMapper;
import com.sheiladiz.mappers.SkillMapper;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;
import com.sheiladiz.services.SkillService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private final SkillService skillService;
    private final SkillMapper skillMapper;
    private final SkillCategoryMapper categoryMapper;

    public SkillController(SkillService skillService, SkillMapper skillMapper, SkillCategoryMapper categoryMapper) {
        this.skillService = skillService;
        this.skillMapper = skillMapper;
        this.categoryMapper = categoryMapper;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = SkillDTO.class))})
    })
    @GetMapping
    public ResponseEntity<List<SkillDTO>> getSkills() {
        List<Skill> skills = skillService.allSkills();
        return ResponseEntity.ok(skillMapper.skillsToSkillDTOs(skills));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SkillDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Habilidad con id {id} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{skillId}")
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable("skillId") Long id) {
        Skill skill = skillService.getSkillById(id);
        return ResponseEntity.ok(skillMapper.toDTO(skill));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = SkillCategoryDTO.class))})
    })
    @GetMapping("/categories")
    public ResponseEntity<List<SkillCategoryDTO>> getCategories() {
        List<SkillCategory> categories = skillService.allCategories();
        return ResponseEntity.ok(categoryMapper.skillCategoriesToSkillCategoryDTOs(categories));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SkillCategoryDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Categoría de habilidad con id {id} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<SkillCategoryDTO> getCategoryById(@PathVariable("categoryId") Long id) {
        SkillCategory category = skillService.getCategoryById(id);
        return ResponseEntity.ok(categoryMapper.toDTO(category));
    }


}

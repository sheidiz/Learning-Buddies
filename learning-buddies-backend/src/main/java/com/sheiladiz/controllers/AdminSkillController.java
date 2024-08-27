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
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/skills")
public class AdminSkillController {
    private final SkillService skillService;
    private final SkillMapper skillMapper;
    private final SkillCategoryMapper categoryMapper;

    public AdminSkillController(SkillService skillService, SkillMapper skillMapper, SkillCategoryMapper categoryMapper) {
        this.skillService = skillService;
        this.skillMapper = skillMapper;
        this.categoryMapper = categoryMapper;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SkillDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Categoría de habilidad no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Ya existe una habilidad con ese nombre",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@Valid @RequestBody SkillDTO skillDTO) {
        Skill savedSkill = skillService.saveSkill(skillDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(skillMapper.toDTO(savedSkill));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SkillDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Habilidad con id {id} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Categoría de habilidad no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable("id") Long id, @RequestBody SkillDTO skillDTO) {
        Skill updatedSkill = skillService.updateSkill(id, skillDTO);
        return ResponseEntity.ok(skillMapper.toDTO(updatedSkill));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Habilidad eliminada exitosamente.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Habilidad con id {id} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/{skillId}")
    public ResponseEntity<?> deleteSkillById(@PathVariable("skillId") Long id) {
        skillService.deleteSkillById(id);
        return ResponseEntity.ok("Habilidad eliminada exitosamente.");
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SkillCategoryDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Ya existe una categoría de habilidad con ese nombre.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/categories")
    public ResponseEntity<SkillCategoryDTO> createCategory(@Valid @RequestBody SkillCategoryDTO skillCategoryDTO) {
        SkillCategory savedCategory = skillService.saveCategory(skillCategoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryMapper.toDTO(savedCategory));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = SkillCategoryDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Categoría de habilidad con id {id} no encontrada",
                    content = @Content)})
    @PutMapping("/categories/{id}")
    public ResponseEntity<SkillCategoryDTO> updateCategory(@PathVariable("id") Long id, @RequestBody SkillCategoryDTO skillCategoryDTO) {
        SkillCategory updatedCategory = skillService.updateCategory(id, skillCategoryDTO);
        return ResponseEntity.ok(categoryMapper.toDTO(updatedCategory));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoría de habilidad eliminada exitosamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría de habilidad con id {id} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("categoryId") Long id) {
        skillService.deleteCategoryById(id);
        return ResponseEntity.ok("Categoría de habilidad eliminada exitosamente");
    }
}

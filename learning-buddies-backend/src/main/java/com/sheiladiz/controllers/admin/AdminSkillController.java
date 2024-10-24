package com.sheiladiz.controllers.admin;

import com.sheiladiz.dtos.skill.RequestSkillCategoryDto;
import com.sheiladiz.dtos.skill.RequestSkillDto;
import com.sheiladiz.dtos.skill.ResponseSkillCategoryDto;
import com.sheiladiz.dtos.skill.ResponseSkillDto;
import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.services.SkillCategoryService;
import com.sheiladiz.services.SkillService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/skills")
public class AdminSkillController {

    private final SkillService skillService;
    private final SkillCategoryService categoryService;

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseSkillDto.class))}),
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
    public ResponseEntity<ResponseSkillDto> createSkill(@Valid @RequestBody RequestSkillDto requestSkillDto) {
        ResponseSkillDto savedSkill = skillService.saveSkill(requestSkillDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSkill);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseSkillDto.class))}),
            @ApiResponse(responseCode = "404", description = "Habilidad con id {id} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Categoría de habilidad no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseSkillDto> updateSkill(@PathVariable("id") Long id, @RequestBody RequestSkillDto requestSkillDto) {
        ResponseSkillDto updatedSkill = skillService.updateSkill(id, requestSkillDto);
        return ResponseEntity.ok(updatedSkill);
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
                    schema = @Schema(implementation = ResponseSkillCategoryDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Ya existe una categoría de habilidad con ese nombre.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping("/categories")
    public ResponseEntity<ResponseSkillCategoryDto> createCategory(@Valid @RequestBody RequestSkillCategoryDto requestSkillCategoryDto) {
        ResponseSkillCategoryDto savedCategory = categoryService.saveCategory(requestSkillCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseSkillCategoryDto.class))}),
            @ApiResponse(responseCode = "404", description = "Categoría de habilidad con id {id} no encontrada",
                    content = @Content)})
    @PutMapping("/categories/{id}")
    public ResponseEntity<ResponseSkillCategoryDto> updateCategory(@PathVariable("id") Long id, @RequestBody RequestSkillCategoryDto requestSkillCategoryDto) {
        ResponseSkillCategoryDto updatedCategory = categoryService.updateCategory(id, requestSkillCategoryDto);
        return ResponseEntity.ok(updatedCategory);
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
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Categoría de habilidad eliminada exitosamente");
    }
}

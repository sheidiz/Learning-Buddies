package com.sheiladiz.controllers.user;

import com.sheiladiz.dtos.skill.ResponseSkillCategoryDto;
import com.sheiladiz.dtos.skill.ResponseSkillDto;
import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.services.SkillCategoryService;
import com.sheiladiz.services.SkillService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/skills")
public class SkillController {

    private final SkillService skillService;
    private final SkillCategoryService categoryService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ResponseSkillDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<ResponseSkillDto>> getSkills() {
        List<ResponseSkillDto> skills = skillService.allSkills();
        return ResponseEntity.ok(skills);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseSkillDto.class))}),
            @ApiResponse(responseCode = "404", description = "Habilidad con id {id} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/{skillId}")
    public ResponseEntity<ResponseSkillDto> getSkillById(@PathVariable("skillId") Long id) {
        ResponseSkillDto skill = skillService.getSkillById(id);
        return ResponseEntity.ok(skill);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ResponseSkillCategoryDto.class))})
    })
    @GetMapping("/categories")
    public ResponseEntity<List<ResponseSkillCategoryDto>> getCategories() {
        List<ResponseSkillCategoryDto> categories = categoryService.allCategories();
        return ResponseEntity.ok(categories);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseSkillCategoryDto.class))}),
            @ApiResponse(responseCode = "404", description = "Categoría de habilidad con id {id} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ResponseSkillCategoryDto> getCategoryById(@PathVariable("categoryId") Long id) {
        ResponseSkillCategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

}

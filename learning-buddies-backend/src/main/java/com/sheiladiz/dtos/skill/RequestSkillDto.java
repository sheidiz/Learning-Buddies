package com.sheiladiz.dtos.skill;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Builder
public record RequestSkillDto(
        @NotBlank(message = "Nombre de habilidad requerido.")
        String name,
        @NotBlank(message = "Categoría/s de habilidad requeridas.")
        List<String> categories,
        String skillType
) {
}

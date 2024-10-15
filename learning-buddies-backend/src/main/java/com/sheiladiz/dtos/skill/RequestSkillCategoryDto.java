package com.sheiladiz.dtos.skill;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RequestSkillCategoryDto(
        @NotEmpty(message = "Nombre de categoría requerido.")
        String name
) {
}

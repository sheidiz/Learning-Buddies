package com.sheiladiz.dtos.skill;

import lombok.Builder;

@Builder
public record ResponseSkillCategoryDto(
        Long id,
        String name
) {
}

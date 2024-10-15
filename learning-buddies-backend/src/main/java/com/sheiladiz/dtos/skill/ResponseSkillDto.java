package com.sheiladiz.dtos.skill;

import lombok.Builder;

import java.util.List;

@Builder
public record ResponseSkillDto(
        Long id,
        String skillType,
        String name,
        List<String> categories,
        List<Long> profilesWhoLearnedThisSkillIds,
        List<Long> profilesLearningThisSkillIds
) {
}

package com.sheiladiz.dtos.profile;

import lombok.Builder;

import java.util.List;

@Builder
public record ResponseProtectedProfileDto(
        Long id,
        Long userId,
        String name,
        String profilePicture,
        String profilePictureBackground,
        String gender,
        String pronouns,
        String country,
        String jobPosition,
        String bio,
        List<String> skillsLearned,
        List<String>skillsToLearn
) {
}

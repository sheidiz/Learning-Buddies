package com.sheiladiz.dtos.profile;

import lombok.Builder;

import java.util.List;

@Builder
public record ResponseProfileDto(
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
        String discordUrl,
        String githubUrl,
        String linkedinUrl,
        String contactEmail,
        List<String> skillsLearned,
        List<String> skillsToLearn
) {
}

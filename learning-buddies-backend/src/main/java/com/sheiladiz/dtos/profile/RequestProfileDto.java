package com.sheiladiz.dtos.profile;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestProfileDto(
        @NotBlank(message = "Nombre requerido.")
        String name,
        @NotEmpty(message = "Foto de perfil requerida.")
        String profilePicture,
        @NotEmpty(message = "Fondo de foto de perfil requerido.")
        String profilePictureBackground,
        @NotEmpty(message = "Género requerido.")
        String gender,
        String pronouns,
        @NotBlank(message = "País requerido.")
        String country,
        @NotBlank(message = "Rol requerido.")
        @Size(max = 100, message = "Posición de trabajo tiene un limite de 100 caracteres.")
        String jobPosition,
        @NotBlank(message = "Biografía requerida.")
        @Size(max = 250, message = "Descripción tiene un limite de 250 caracteres.")
        String bio,
        String discordUrl,
        String githubUrl,
        String linkedinUrl,
        String contactEmail,
        List<String> skillsLearned,
        List<String> skillsToLearn
) {
}
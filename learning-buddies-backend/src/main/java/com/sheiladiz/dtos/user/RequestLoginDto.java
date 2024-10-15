package com.sheiladiz.dtos.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestLoginDto(
        @NotEmpty(message = "Email requerido.")
        @Email(message = "Email invalido.")
        @Column(unique = true)
        String email,

        @NotEmpty(message = "Contraseña requerida.")
        @Size(min = 6, message = "Contraseña debe contener al menos 6 caracteres.")
        String password
) {
}

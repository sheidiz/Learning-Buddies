package com.sheiladiz.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestChangePassword(
        @NotEmpty(message = "Contraseña anterior requerida.")
        @Size(min = 6, message = "Contraseña anterior debe contener al menos 6 caracteres.")
        String currentPassword,

        @NotEmpty(message = "Contraseña nueva requerida.")
        @Size(min = 6, message = "Contraseña nueva debe contener al menos 6 caracteres.")
        String newPassword
) {
}

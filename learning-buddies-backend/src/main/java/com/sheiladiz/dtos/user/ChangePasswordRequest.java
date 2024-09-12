package com.sheiladiz.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
	@NotEmpty(message = "Contraseña anterior requerida.")
	@Size(min = 6, message = "Contraseña anterior debe contener al menos 6 caracteres.")
	String currentPassword;
	
	@NotEmpty(message = "Contraseña nueva requerida.")
	@Size(min = 6, message = "Contraseña nueva debe contener al menos 6 caracteres.")
	String newPassword;
}

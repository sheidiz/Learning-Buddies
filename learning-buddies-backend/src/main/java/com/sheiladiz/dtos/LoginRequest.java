package com.sheiladiz.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

	@NotBlank(message = "Email requerido.")
	@Email(message = "Email invalido.")
	String email;

	@NotBlank(message = "Contraseña requerida.")
	String password;

}

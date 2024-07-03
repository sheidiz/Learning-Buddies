package com.sheiladiz.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

	@NotEmpty(message = "Email requerido.")
	@Email(message = "Email invalido.")
	String email;

	@NotEmpty(message = "Contraseña requerida.")
	String password;

}

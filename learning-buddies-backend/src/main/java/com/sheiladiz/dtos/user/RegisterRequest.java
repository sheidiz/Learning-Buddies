package com.sheiladiz.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {

	@NotEmpty(message = "Email requerido.")
	@Email(message = "Email invalido.")
	@Column(unique = true)
	private String email;

	@NotEmpty(message = "Contraseña requerida.")
	@Size(min = 6, message = "Contraseña debe contener al menos 6 caracteres.")
	private String password;

	@Builder.Default
	@Setter
	private String authProvider = "local";

	public String getAuthProvider() {
		if (authProvider == null || authProvider.isEmpty()) {
			return "local";
		}
		return authProvider;
	}
}

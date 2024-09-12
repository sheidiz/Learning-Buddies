package com.sheiladiz.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sheiladiz.dtos.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
	private Long id;
	private String email;
	private String authProvider;
	private ProfileDTO profile;

	public UserDTO(Long id, String email, String authProvider) {
		this.id = id;
		this.email = email;
		this.authProvider = authProvider;
	}
}
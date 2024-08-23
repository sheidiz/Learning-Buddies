package com.sheiladiz.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillCategoryDTO {
	private Long id;
	@NotEmpty(message = "Nombre requerido.")
	private String name;
	private List<String> skillNames;
}

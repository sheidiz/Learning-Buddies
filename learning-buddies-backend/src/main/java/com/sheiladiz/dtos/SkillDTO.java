package com.sheiladiz.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SkillDTO {
	private Long id;
	private String skillType;
	@NotEmpty(message = "Nombre requerido.")
	private String name;
	@NotNull(message = "Categorías requeridas.")
	private List<String> categories;
	private List<Long> profilesWhoLearnedThisSkillIds;
	private List<Long> profilesLearningThisSkillIds;
}

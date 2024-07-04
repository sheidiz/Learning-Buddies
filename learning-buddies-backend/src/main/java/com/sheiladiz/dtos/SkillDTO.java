package com.sheiladiz.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

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
	private String name;
	private List<String> categories;
	private List<Long> profilesWhoLearnedThisSkillIds;
	private List<Long> profilesLearningThisSkillIds;
}

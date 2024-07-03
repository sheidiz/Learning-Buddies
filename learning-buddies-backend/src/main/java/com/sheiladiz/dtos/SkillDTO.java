package com.sheiladiz.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillDTO {
	
	private Long id;
	private String skillType;
	private String name;
	private List<String> categories;
	private List<Long> profilesWhoLearnedThisSkillIds;
	private List<Long> profilesLearningThisSkillIds;
}

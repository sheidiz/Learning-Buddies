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
	private List<Long> categories;
	private List<Long> usersWhoLearnedThisSkillIds;
	private List<Long> usersLearningThisSkillIds;
}

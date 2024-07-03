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
public class SkillCategoryDTO {
	
	private Long id;
	private String name;
	private List<String> skillNames;
}

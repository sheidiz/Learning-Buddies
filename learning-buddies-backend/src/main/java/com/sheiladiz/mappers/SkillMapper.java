package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.SkillDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.SkillCategory;

@Component
public class SkillMapper {

	public SkillDTO skillToSkillDTO(Skill skill) {
		return SkillDTO.builder().id(skill.getId()).skillType(skill.getSkillType()).name(skill.getName())
				.categories(mapCategories(skill.getCategories()))
				.profilesWhoLearnedThisSkillIds(mapUsers(skill.getProfilesWhoLearnedThisSkill()))
				.profilesLearningThisSkillIds(mapUsers(skill.getProfilesLearningThisSkill())).build();
	}

	private List<String> mapCategories(List<SkillCategory> categories) {
		return categories.stream().map(SkillCategory::getName).collect(Collectors.toList());
	}

	private List<Long> mapUsers(List<Profile> profiles) {
		return profiles.stream().map(Profile::getId).collect(Collectors.toList());
	}

	public List<SkillDTO> skillsToSkillDTOs(List<Skill> skills) {
		return skills.stream().map(this::skillToSkillDTO).collect(Collectors.toList());
	}

}
package com.sheiladiz.mappers;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.services.SkillService;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

	private final SkillService skillService;

	public ProfileDTO toDTO(Profile profile) {
		ProfileDTO profileDTO = toProtectedDTO(profile);

		if (profile.getDiscordUrl() != null) {
			profileDTO.setDiscordUrl(profile.getDiscordUrl());
		}

		if (profile.getGithubUrl() != null) {
			profileDTO.setGithubUrl(profile.getGithubUrl());
		}

		if (profile.getLinkedinUrl() != null) {
			profileDTO.setLinkedinUrl(profile.getLinkedinUrl());
		}

		if (profile.getContactEmail() != null) {
			profileDTO.setContactEmail(profile.getContactEmail());
		}

		return profileDTO;
	}

	public ProfileDTO toProtectedDTO(Profile profile) {
		ProfileDTO.ProfileDTOBuilder builder = ProfileDTO.builder().id(profile.getId()).name(profile.getName())
				.bio(profile.getBio()).profilePicture(profile.getProfilePicture())
				.profilePictureBackground(profile.getProfilePictureBackground()).country(profile.getCountry())
				.gender(profile.getGender()).jobPosition(profile.getJobPosition());

		if (profile.getPronouns() != null) {
			builder.pronouns(profile.getPronouns());
		}

		if (profile.getSkillsLearned() != null && !profile.getSkillsLearned().isEmpty()) {
			builder.skillsLearned(mapSkills(profile.getSkillsLearned()));
		} else {
			builder.skillsLearned(Collections.emptyList());
		}

		if (profile.getSkillsToLearn() != null && !profile.getSkillsToLearn().isEmpty()) {
			builder.skillsToLearn(mapSkills(profile.getSkillsToLearn()));
		} else {
			builder.skillsToLearn(Collections.emptyList());
		}

		return builder.build();
	}

	public Profile toEntity(ProfileDTO profileDTO) {
		Profile.ProfileBuilder builder = Profile.builder().id(profileDTO.getId()).name(profileDTO.getName())
				.bio(profileDTO.getBio()).profilePicture(profileDTO.getProfilePicture())
				.profilePictureBackground(profileDTO.getProfilePictureBackground()).country(profileDTO.getCountry())
				.gender(profileDTO.getGender()).jobPosition(profileDTO.getJobPosition());

		if (profileDTO.getPronouns() != null) {
			builder.pronouns(profileDTO.getPronouns());
		}

		if (profileDTO.getDiscordUrl() != null) {
			builder.discordUrl(profileDTO.getDiscordUrl());
		}

		if (profileDTO.getGithubUrl() != null) {
			builder.githubUrl(profileDTO.getGithubUrl());
		}

		if (profileDTO.getLinkedinUrl() != null) {
			builder.linkedinUrl(profileDTO.getLinkedinUrl());
		}

		if (profileDTO.getContactEmail() != null) {
			builder.contactEmail(profileDTO.getContactEmail());
		}

		if (profileDTO.getSkillsLearned() != null) {
			builder.skillsLearned(mapSkillNames(profileDTO.getSkillsLearned()));
		} else {
			builder.skillsLearned(new HashSet<>());
		}

		if (profileDTO.getSkillsToLearn() != null) {
			builder.skillsToLearn(mapSkillNames(profileDTO.getSkillsToLearn()));
		} else {
			builder.skillsToLearn(new HashSet<>());
		}

		return builder.build();
	}

	private List<String> mapSkills(Set<Skill> skills) {
		return skills.stream().map(Skill::getName).collect(Collectors.toList());
	}

	public Set<Skill> mapSkillNames(List<String> skillNames) {
	    return skillService.getSkillsByNames(skillNames);
	}

	public List<ProfileDTO> profilesToProfileDTOs(List<Profile> profiles) {
		return profiles.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<ProfileDTO> profilesToProtectedProfileDTOs(List<Profile> profiles) {
		return profiles.stream().map(this::toProtectedDTO).collect(Collectors.toList());
	}

}
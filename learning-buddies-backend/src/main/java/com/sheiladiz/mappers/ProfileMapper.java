package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.services.SkillService;

@Component
public class ProfileMapper {

	@Autowired
	@Lazy
	private SkillService skillService;

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
		ProfileDTO.ProfileDTOBuilder builder = ProfileDTO.builder()
				.id(profile.getId())
				.name(profile.getName())
				.profilePicture(profile.getProfilePicture())
				.profilePictureBackground(profile.getProfilePictureBackground());

		if (profile.getGender() != null) {
			builder.gender(profile.getGender());
		}

		if (profile.getPronouns() != null) {
			builder.pronouns(profile.getPronouns());
		}

		if (profile.getCountry() != null) {
			builder.country(profile.getCountry());
		}

		if (profile.getJobPosition() != null) {
			builder.jobPosition(profile.getJobPosition());
		}

		if (profile.getBio() != null) {
			builder.bio(profile.getBio());
		}

		if (profile.getSkillsLearned() != null) {
			builder.skillsLearned(mapSkills(profile.getSkillsLearned()));
		}

		if (profile.getSkillsToLearn() != null) {
			builder.skillsToLearn(mapSkills(profile.getSkillsToLearn()));
		}

		return builder.build();
	}

	public Profile toEntity(ProfileDTO profileDTO) {
		Profile.ProfileBuilder builder = Profile.builder()
				.id(profileDTO.getId())
				.name(profileDTO.getName())
				.profilePicture(profileDTO.getProfilePicture())
				.profilePictureBackground(profileDTO.getProfilePictureBackground());

		if (profileDTO.getGender() != null) {
			builder.gender(profileDTO.getGender());
		}

		if (profileDTO.getPronouns() != null) {
			builder.pronouns(profileDTO.getPronouns());
		}

		if (profileDTO.getCountry() != null) {
			builder.country(profileDTO.getCountry());
		}

		if (profileDTO.getJobPosition() != null) {
			builder.jobPosition(profileDTO.getJobPosition());
		}

		if (profileDTO.getBio() != null) {
			builder.bio(profileDTO.getBio());
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
		}

		if (profileDTO.getSkillsToLearn() != null) {
			builder.skillsToLearn(mapSkillNames(profileDTO.getSkillsToLearn()));
		}

		return builder.build();
	}

	private List<String> mapSkills(List<Skill> skills) {
		return skills.stream().map(Skill::getName).collect(Collectors.toList());
	}

	public List<Skill> mapSkillNames(List<String> skillNames) {
		return skillNames.stream().map(skillService::getSkillByName).collect(Collectors.toList());
	}

	public List<ProfileDTO> profilesToProfileDTOs(List<Profile> profiles) {
		return profiles.stream().map(this::toDTO).collect(Collectors.toList());
	}

	public List<ProfileDTO> profilesToProtectedProfileDTOs(List<Profile> profiles) {
		return profiles.stream().map(this::toProtectedDTO).collect(Collectors.toList());
	}

}
package com.sheiladiz.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.SkillService;
import com.sheiladiz.services.UserService;

@Component
public class ProfileMapper {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private SkillService skillService;

	@Autowired
	private UserMapper userMapper;

	public ProfileDTO profileToProfileDTO(Profile profile) {
		ProfileDTO.ProfileDTOBuilder builder = ProfileDTO.builder()
				.id(profile.getId())
				.name(profile.getName());

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
		
		if (profile.getDiscordUrl() != null) {
			builder.discordUrl(profile.getDiscordUrl());
		}
		
		if (profile.getGithubUrl() != null) {
			builder.githubUrl(profile.getGithubUrl());
		}
		
		if (profile.getLinkedinUrl() != null) {
			builder.linkedinUrl(profile.getLinkedinUrl());
		}
		
		if (profile.getContactEmail() != null) {
			builder.contactEmail(profile.getContactEmail());
		}
		
		if (profile.getSkillsLearned() != null) {
			builder.skillsLearned(mapSkills(profile.getSkillsLearned()));
		}
		
		if (profile.getSkillsToLearn() != null) {
			builder.skillsLearned(mapSkills(profile.getSkillsToLearn()));
		}
		
		if (profile.getFriends() != null) {
			builder.friendIds(mapFriends(profile.getFriends()));
		}
		
		return builder.build();
	}

	public Profile profileDTOToProfile(ProfileDTO profileDTO) {
		UserEntity user = userMapper.userDTOToUserEntity(userService.getUserById(profileDTO.getId()));

		Profile.ProfileBuilder builder = Profile.builder()
				.id(profileDTO.getId())
				.user(user)
				.name(profileDTO.getName());

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
			builder.skillsLearned(mapSkillNames(profileDTO.getSkillsToLearn()));
		}
		
		if (profileDTO.getFriendIds() != null) {
			builder.friends(mapFriendsIds(profileDTO.getFriendIds()));
		}
		
		return builder.build();
	}

	private List<String> mapSkills(List<Skill> skills) {
		return skills.stream().map(Skill::getName).collect(Collectors.toList());
	}

	public List<Skill> mapSkillNames(List<String> skillNames) {
		return skillNames.stream().map(skillService::findSkillByName).collect(Collectors.toList());
	}

	private List<Long> mapFriends(List<Profile> friends) {
		return friends.stream().map(Profile::getId).collect(Collectors.toList());
	}

	private List<Profile> mapFriendsIds(List<Long> friends) {
		return friends.stream().map(profileService::findProfileById).collect(Collectors.toList());
	}

	public List<ProfileDTO> profilesToProfileDTOs(List<Profile> profiles) {
		return profiles.stream().map(this::profileToProfileDTO).collect(Collectors.toList());
	}

	public List<Profile> profileDTOsToProfiles(List<ProfileDTO> profileDTOs) {
		return profileDTOs.stream().map(this::profileDTOToProfile).collect(Collectors.toList());
	}

}
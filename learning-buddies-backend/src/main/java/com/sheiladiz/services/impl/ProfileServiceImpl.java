package com.sheiladiz.services.impl;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.repositories.UserRepository;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.SkillService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
	
	private final ProfileRepository profileRepository;
	private final UserRepository userRepository;
	private final SkillService skillService;
	private final ProfileMapper profileMapper;

	public void isProfileExistsByUser(User user) {
		if (profileRepository.existsByUser(user)) {
			throw new ResourceAlreadyExistsException("Perfil ya existe");
		}
	}

	public Profile saveProfile(ProfileDTO newProfile, User user) {
		if (user.getProfileId() != null) {
			throw new ResourceAlreadyExistsException("El perfil para este usuario ya existe.");
		}
		Profile profile = profileMapper.toEntity(newProfile);
		profile.setUser(user);
		
		Profile savedProfile = profileRepository.save(profile);
		
        user.setProfileId(savedProfile.getId());
        userRepository.save(user);
		
		return profileRepository.save(savedProfile);
	}

	public List<Profile> allProfiles() {
		return profileRepository.findAll();
	}

	public Profile getProfileByUser(User user) {
		return profileRepository.findByUser(user).orElseThrow(
				() -> new ResourceNotFoundException("Perfil no encontrado para usuario con email: " + user.getEmail()));
	}

	public Profile getProfileByUserId(Long id) {
		return profileRepository.findByUserId(id).orElseThrow(
				() -> new ResourceNotFoundException("Perfil no encontrado para usuario con id: " + id));
	}

	public Profile getProfileByUserEmail(String email) {
		return profileRepository.findByUserEmail(email).orElseThrow(
				() -> new ResourceNotFoundException("Perfil no encontrado para usuario con email: " + email));
	}

	public Profile getProfileById(Long id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado para usuario con id: " + id));
	}

	public List<ProfileDTO> getProfilesBySkills(List<String> skillsLearned, List<String> skillsToLearn) {
		List<Profile> profiles = profileRepository.findAll();
		
		System.out.println(skillsLearned);
		System.out.println(skillsToLearn);

	    return profiles.stream()
	        .filter(profile -> (skillsLearned == null || skillsLearned.isEmpty() || 
	                            profile.getSkillsLearned().stream().anyMatch(skill -> skillsLearned.contains(skill.getName()))) &&
	                           (skillsToLearn == null || skillsToLearn.isEmpty() || 
	                            profile.getSkillsToLearn().stream().anyMatch(skill -> skillsToLearn.contains(skill.getName()))))
	        .map(profileMapper::toProtectedDTO)
	        .collect(Collectors.toList());
	}

	public Profile updateProfile(Profile existingProfile, ProfileDTO profileDTO) {

		Profile updatedProfile = profileMapper.toEntity(profileDTO);
		
		existingProfile.setName(updatedProfile.getName());
		existingProfile.setBio(updatedProfile.getBio());
		existingProfile.setJobPosition(updatedProfile.getJobPosition());
		existingProfile.setCountry(updatedProfile.getCountry());
		existingProfile.setGender(updatedProfile.getGender());
		existingProfile.setProfilePicture(updatedProfile.getProfilePicture());
		existingProfile.setProfilePictureBackground(updatedProfile.getProfilePictureBackground());
		existingProfile.setSkillsLearned(updatedProfile.getSkillsLearned());
		existingProfile.setSkillsToLearn(updatedProfile.getSkillsToLearn());
		
		if(updatedProfile.getGithubUrl() != null) {
			existingProfile.setGithubUrl(updatedProfile.getGithubUrl());
		}
		if(updatedProfile.getLinkedinUrl() != null) {
			existingProfile.setLinkedinUrl(updatedProfile.getLinkedinUrl());
		}
		if(updatedProfile.getDiscordUrl() != null) {
			existingProfile.setDiscordUrl(updatedProfile.getDiscordUrl());
		}
		if(updatedProfile.getContactEmail() != null) {
			existingProfile.setContactEmail(updatedProfile.getContactEmail());
		}

		return profileRepository.save(existingProfile);
	}

	public Profile updateProfileSkills(String type, Long profileId, List<String> skillNames) {
		Profile profile = getProfileById(profileId);
		if (type.equals("learned")){
			profile.setSkillsLearned(new HashSet<>());
		}else{
			profile.setSkillsToLearn(new HashSet<>());
		}
		for (String name : skillNames) {
			Skill skill = skillService.getSkillByName(name);

			if (type.equals("learned")) {
				profile.getSkillsLearned().add(skill);
			} else {
				profile.getSkillsToLearn().add(skill);
			}
		}
		return profileRepository.save(profile);
	}

	public void deleteProfile(Long userId) {
		Profile profile = profileRepository.findByUserId(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado para usuario con id " + userId));
		profileRepository.delete(profile);
	}

}

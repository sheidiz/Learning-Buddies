package com.sheiladiz.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.Skill;
import com.sheiladiz.models.User;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.SkillService;

@Service
public class ProfileServiceImpl implements ProfileService {
	private final ProfileRepository profileRepository;
	private final SkillService skillService;
	private final ProfileMapper profileMapper;

	public ProfileServiceImpl(ProfileRepository profileRepository, SkillService skillService, ProfileMapper profileMapper) {
		this.profileRepository = profileRepository;
		this.skillService = skillService;
		this.profileMapper = profileMapper;
	}

	public void isProfileExistsByUser(User user) {
		if (profileRepository.existsByUser(user)) {
			throw new ResourceAlreadyExistsException("Perfil ya existe");
		}
	}

	public Profile saveProfile(ProfileDTO newProfile, User user) {
		Profile profile = profileMapper.toEntity(newProfile);
		profile.setUser(user);
		return profileRepository.save(profile);
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

	public Profile getProfileById(Long id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado para usuario con id: " + id));
	}

	public List<Profile> listProfilesByJobPositionContaining(String job) {
		return profileRepository.findByJobPositionContaining(job);
	}

	public Profile updateProfile(Long profileId, ProfileDTO profileDTO) {
		Profile existingProfile = profileRepository.findById(profileId).orElseThrow(
				() -> new ResourceNotFoundException("Perfil no encontrado para usuario con id: " + profileId));

		Optional.ofNullable(profileDTO.getName()).ifPresent(existingProfile::setName);
		Optional.ofNullable(profileDTO.getProfilePicture()).ifPresent(existingProfile::setProfilePicture);
		Optional.ofNullable(profileDTO.getGender()).ifPresent(existingProfile::setGender);
		Optional.ofNullable(profileDTO.getPronouns()).ifPresent(existingProfile::setPronouns);
		Optional.ofNullable(profileDTO.getCountry()).ifPresent(existingProfile::setCountry);
		Optional.ofNullable(profileDTO.getJobPosition()).ifPresent(existingProfile::setJobPosition);
		Optional.ofNullable(profileDTO.getBio()).ifPresent(existingProfile::setBio);
		Optional.ofNullable(profileDTO.getDiscordUrl()).ifPresent(existingProfile::setDiscordUrl);
		Optional.ofNullable(profileDTO.getGithubUrl()).ifPresent(existingProfile::setGithubUrl);
		Optional.ofNullable(profileDTO.getLinkedinUrl()).ifPresent(existingProfile::setLinkedinUrl);
		Optional.ofNullable(profileDTO.getContactEmail()).ifPresent(existingProfile::setContactEmail);

		return profileRepository.save(existingProfile);
	}

	public Profile addProfileSkills(String type, Long profileId, List<String> skillNames) {
		Profile profile = getProfileById(profileId);

		for (String name : skillNames) {
			Skill skill = skillService.getSkillEntityByName(name);

			if (type.equals("learned")) {
				if (!profile.getSkillsLearned().contains(skill)) {
					profile.getSkillsLearned().add(skill);
				}
			} else {
				if (!profile.getSkillsToLearn().contains(skill)) {
					profile.getSkillsToLearn().add(skill);
				}
			}
		}
		return profileRepository.save(profile);
	}
	public Profile updateProfileSkills(String type, Long profileId, List<String> skillNames) {
		Profile profile = getProfileById(profileId);
		if (type.equals("learned")){
			profile.setSkillsLearned(new ArrayList<>());
		}else{
			profile.setSkillsToLearn(new ArrayList<>());
		}
		for (String name : skillNames) {
			Skill skill = skillService.getSkillEntityByName(name);

			if (type.equals("learned")) {
				if (!profile.getSkillsLearned().contains(skill)) {
					profile.getSkillsLearned().add(skill);
				}
			} else {
				if (!profile.getSkillsToLearn().contains(skill)) {
					profile.getSkillsToLearn().add(skill);
				}
			}
		}
		return profileRepository.save(profile);
	}

	public void deleteProfile(Long id) {
		Profile profile = profileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Perfil no encontrado para usuario con id: " + id));
		profileRepository.delete(profile);
	}

}

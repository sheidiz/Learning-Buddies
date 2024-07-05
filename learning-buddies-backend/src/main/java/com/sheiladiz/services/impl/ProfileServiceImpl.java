package com.sheiladiz.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.exceptions.profile.ProfileAlreadyCreatedException;
import com.sheiladiz.exceptions.profile.ProfileNotFoundException;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.services.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ProfileMapper profileMapper;

	public void isProfileExistsByUser(UserEntity user) {
		if (profileRepository.existsByUser(user)) {
			throw new ProfileAlreadyCreatedException("Perfil ya existe");
		}
	}

	public ProfileDTO saveProfile(ProfileDTO newProfile, UserEntity existingUser) {
		Profile profile = profileMapper.profileDTOToProfile(newProfile);
		profile.setUser(existingUser);
		Profile savedProfile = profileRepository.save(profile);
		return profileMapper.profileToProfileDTO(savedProfile);
	}

	public List<ProfileDTO> allProfiles() {
		return profileMapper.profilesToProfileDTOs(profileRepository.findAll());
	}

	public ProfileDTO getProfileByUser(UserEntity user) {
		Profile profile = profileRepository.findByUser(user).orElseThrow(
				() -> new ProfileNotFoundException("Perfil no encontrado para usuario con email: " + user.getEmail()));
		return profileMapper.profileToProfileDTO(profile);
	}

	public ProfileDTO getProfileById(Long id) {
		Profile profile = profileRepository.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Perfil no encontrado para usuario con id: " + id));
		return profileMapper.profileToProfileDTO(profile);
	}

	public Profile getProfileEntityById(Long id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Perfil no encontrado para usuario con id: " + id));
	}

	public List<ProfileDTO> listProfilesByJobPositionContaining(String job) {
		List<Profile> profiles = profileRepository.findByJobPositionContaining(job);
		return profileMapper.profilesToProfileDTOs(profiles);
	}

	public ProfileDTO updateProfile(Long profileId, ProfileDTO profileDTO) {
		Profile existingProfile = profileRepository.findById(profileId).orElseThrow(
				() -> new ProfileNotFoundException("Perfil no encontrado para usuario con id: " + profileId));

		if (profileDTO.getName() != null) {
			existingProfile.setName(profileDTO.getName());
		}
		if (profileDTO.getGender() != null) {
			existingProfile.setGender(profileDTO.getGender());
		}
		if (profileDTO.getPronouns() != null) {
			existingProfile.setPronouns(profileDTO.getPronouns());
		}
		if (profileDTO.getCountry() != null) {
			existingProfile.setCountry(profileDTO.getCountry());
		}
		if (profileDTO.getJobPosition() != null) {
			existingProfile.setJobPosition(profileDTO.getJobPosition());
		}
		if (profileDTO.getBio() != null) {
			existingProfile.setBio(profileDTO.getBio());
		}
		if (profileDTO.getDiscordUrl() != null) {
			existingProfile.setDiscordUrl(profileDTO.getDiscordUrl());
		}
		if (profileDTO.getGithubUrl() != null) {
			existingProfile.setGithubUrl(profileDTO.getGithubUrl());
		}
		if (profileDTO.getLinkedinUrl() != null) {
			existingProfile.setLinkedinUrl(profileDTO.getLinkedinUrl());
		}
		if (profileDTO.getContactEmail() != null) {
			existingProfile.setContactEmail(profileDTO.getContactEmail());
		}

		Profile updatedProfile = profileRepository.save(existingProfile);

		return profileMapper.profileToProfileDTO(updatedProfile);
	}

	public void deleteProfile(Long id) {
		Profile profile = profileRepository.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Perfil no encontrado para usuario con id: " + id));
		profileRepository.delete(profile);
	}

}

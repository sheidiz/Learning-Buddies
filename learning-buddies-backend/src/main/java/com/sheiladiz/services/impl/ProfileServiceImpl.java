package com.sheiladiz.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.exceptions.profile.ProfileAlreadyCreatedException;
import com.sheiladiz.exceptions.profile.ProfileNotFoundException;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.ProfileRepository;
import com.sheiladiz.services.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	
	public void isProfileExistsByUser(UserEntity user) {
		if(profileRepository.existsByUser(user)) {
			throw new ProfileAlreadyCreatedException("Perfil ya existe");
		}
	}
	
	public Profile saveProfile(Profile newProfile) {
		return profileRepository.save(newProfile);
	}

	public List<Profile> allProfiles() {
		return profileRepository.findAll();
	}

	public Profile findProfileByUser(UserEntity user) {
		return profileRepository.findByUser(user).orElseThrow(
				() -> new ProfileNotFoundException("Perfil no encontrado para usuario con email: " + user.getEmail()));
	}

	public Profile findProfileById(Long id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new ProfileNotFoundException("Perfil no encontrado para usuario con id: " + id));
	}

	public List<Profile> listProfilesByJobPositionContaining(String job) {
		return profileRepository.findByJobPositionContaining(job);
	}

	public void deleteProfile(Long id) {
		Profile profile = findProfileById(id);
		profileRepository.delete(profile);
	}
}

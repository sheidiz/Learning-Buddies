package com.sheiladiz.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.repositories.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	public Profile saveProfile(Profile newProfile) {
		return profileRepository.save(newProfile);
	}

	public List<Profile> allProfiles() {
		return profileRepository.findAll();
	}

	public Profile findByUser(UserEntity user) {
		return profileRepository.findByUser(user)
				.orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));
	}

	public Profile findById(Long id) {
		return profileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));
	}

	public List<Profile> listProfilesByJobPositionContaining(String job) {
		return profileRepository.findByJobPositionContaining(job);
	}

	public Profile updateProfile(Profile profile) {
		if (profileRepository.existsById(profile.getId())) {
			return profileRepository.save(profile);
		} else {
			throw new IllegalArgumentException("Perfil no encontrado");
		}
	}

	public void deleteProfile(Long id) {
		profileRepository.deleteById(id);
	}
}
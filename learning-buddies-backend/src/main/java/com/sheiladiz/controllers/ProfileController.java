package com.sheiladiz.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.exceptions.profile.ProfileAlreadyCreatedException;
import com.sheiladiz.exceptions.profile.ProfileNotFoundException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.models.UserEntity;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileMapper profileMapper;

	@PostMapping()
	public ResponseEntity<?> createProfile(@Valid @RequestBody ProfileDTO profileDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			UserEntity user = userService.findUserById(profileDTO.getUserId());
			profileService.isProfileExistsByUser(user);
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (ProfileAlreadyCreatedException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}

		Profile profile = profileMapper.profileDTOToProfile(profileDTO);
		Profile savedProfile = profileService.saveProfile(profile);
		ProfileDTO savedProfileDTO = profileMapper.profileToProfileDTO(savedProfile);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProfileDTO);
	}

	@GetMapping()
	public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
		List<Profile> profiles = profileService.allProfiles();
		List<ProfileDTO> profileDTOs = profileMapper.profilesToProfileDTOs(profiles);
		return ResponseEntity.ok(profileDTOs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProfileById(@PathVariable("id") Long id) {
		try {
			Profile profile = profileService.findProfileById(id);
			ProfileDTO profileDto = profileMapper.profileToProfileDTO(profile);
			return ResponseEntity.ok(profileDto);
		} catch (ProfileNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProfile(@PathVariable("id") Long id, @RequestBody ProfileDTO profileDTO) {
		try {
			Profile existingProfile = profileService.findProfileById(id);
			profileDTO.setId(existingProfile.getId());

			Profile profileToUpdate = profileMapper.profileDTOToProfile(profileDTO);
			Profile updatedProfile = profileService.saveProfile(profileToUpdate);
			ProfileDTO updatedProfileDTO = profileMapper.profileToProfileDTO(updatedProfile);
			return ResponseEntity.ok(updatedProfileDTO);
		} catch (ProfileNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProfileById(@PathVariable("id") Long id) {
		try {
			profileService.deleteProfile(id);
			return ResponseEntity.ok("Perfil eliminado exitosamente");
		} catch (ProfileNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}

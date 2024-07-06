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
import com.sheiladiz.dtos.SkillsRequest;
import com.sheiladiz.exceptions.profile.ProfileAlreadyCreatedException;
import com.sheiladiz.exceptions.profile.ProfileNotFoundException;
import com.sheiladiz.exceptions.skill.SkillNotFoundException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
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

	@PostMapping("{userId}")
	public ResponseEntity<?> createProfile(@PathVariable("userId") Long userId,
			@Valid @RequestBody ProfileDTO profileDTO, BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors().stream().map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			UserEntity user = userService.getUserEntityById(userId);

			if (user.getProfile() != null) {
				throw new ProfileAlreadyCreatedException("El perfil para este usuario ya existe");
			}
			ProfileDTO savedProfileDTO = profileService.saveProfile(profileDTO, user);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedProfileDTO);
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (ProfileAlreadyCreatedException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
		List<ProfileDTO> profileDTOs = profileService.allProfiles();
		return ResponseEntity.ok(profileDTOs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProfileById(@PathVariable("id") Long id) {
		try {
			ProfileDTO profileDto = profileService.getProfileById(id);
			return ResponseEntity.ok(profileDto);
		} catch (ProfileNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProfile(@PathVariable("id") Long id, @RequestBody ProfileDTO profileDTO) {
		try {
			ProfileDTO updateProfileDTO = profileService.updateProfile(id, profileDTO);
			return ResponseEntity.ok(updateProfileDTO);
		} catch (ProfileNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}/addSkills")
	public ResponseEntity<?> addLearnedSkillsToProfile(@PathVariable("id") Long id,
			@RequestBody SkillsRequest request) {
		try {
			ProfileDTO updateProfileDTO = null;
			if (request.getSkillsLearned() != null) {
				updateProfileDTO = profileService.addProfileSkills("learned", id, request.getSkillsLearned());
			}
			if (request.getSkillsToLearn() != null) {
				updateProfileDTO = profileService.addProfileSkills("learning", id, request.getSkillsToLearn());
			}

			return ResponseEntity.ok(updateProfileDTO);
		} catch (ProfileNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		} catch (SkillNotFoundException ex) {
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

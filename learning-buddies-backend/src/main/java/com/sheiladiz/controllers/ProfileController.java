package com.sheiladiz.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.sheiladiz.exceptions.ResourceAlreadyExistsException;
import com.sheiladiz.exceptions.ResourceNotFoundException;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import com.sheiladiz.models.User;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {
	private final ProfileService profileService;
	private final UserService userService;
	private final ProfileMapper profileMapper;

	public ProfileController(ProfileService profileService, UserService userService, ProfileMapper profileMapper) {
		this.profileService = profileService;
		this.userService = userService;
		this.profileMapper = profileMapper;
	}

	@ApiResponses({
			@ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = ProfileDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Missing data on request.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found.",
					content = @Content),
			@ApiResponse(responseCode = "409", description = "Profile already created.",
					content = @Content)})
	@PostMapping
	public ResponseEntity<?> createProfile(@Valid @RequestBody ProfileDTO profileDTO, BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = result.getAllErrors().stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return ResponseEntity.badRequest().body(errors);
		}

		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User authenticatedUser = userService.getUserByEmail(email);

			if (authenticatedUser.getProfile() != null) {
				throw new ResourceAlreadyExistsException("El perfil para este usuario ya existe");
			}

			Profile savedProfile = profileService.saveProfile(profileDTO, authenticatedUser);
			return ResponseEntity.status(HttpStatus.CREATED).body(profileMapper.toDTO(savedProfile));
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
		List<Profile> profiles = profileService.allProfiles();
		return ResponseEntity.ok(profileMapper.profilesToProfileDTOs(profiles));
	}

	@ApiResponses({
			@ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = ProfileDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "User not found.",
					content = @Content)})
	@GetMapping("/{email}")
	public ResponseEntity<?> getProfileByUserEmail(@PathVariable("email") String email) {
		try {
			User user = userService.getUserByEmail(email);
			Profile profile = profileService.getProfileByUser(user);
			return ResponseEntity.ok(profileMapper.toDTO(profile));
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = ProfileDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Profile not found.",
					content = @Content)})
	@PutMapping
	public ResponseEntity<?> updateProfile(@RequestBody ProfileDTO profileDTO) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User authenticatedUser = userService.getUserByEmail(email);
			Profile updateProfile = profileService.updateProfile(authenticatedUser.getProfile().getId(), profileDTO);
			return ResponseEntity.ok(profileMapper.toDTO(updateProfile));
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@ApiResponses({
			@ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = ProfileDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Profile or skill not found.",
					content = @Content)})
	@PutMapping("/addSkills")
	public ResponseEntity<?> addSkillsToProfile(@RequestBody SkillsRequest request) {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User authenticatedUser = userService.getUserByEmail(email);
			Profile updateProfile = profileService.getProfileByUserId(authenticatedUser.getId());

			if(request.getSkillsLearned() == null && request.getSkillsToLearn() == null){
				return ResponseEntity.badRequest().body("Se necesita ingresar skillsLearned y/o skillsToLearn");
			}
			if (request.getSkillsLearned() != null) {
				updateProfile = profileService.addProfileSkills("learned", updateProfile.getId(), request.getSkillsLearned());
			}
			if (request.getSkillsToLearn() != null) {
				updateProfile = profileService.addProfileSkills("learning", updateProfile.getId(), request.getSkillsToLearn());
			}

			return ResponseEntity.ok(profileMapper.toDTO(updateProfile));

	}

	@ApiResponses({
			@ApiResponse(responseCode = "201", content = { @Content(mediaType = "application/json",
					schema = @Schema(implementation = ProfileDTO.class)) }),
			@ApiResponse(responseCode = "404", description = "Profile or skill not found.",
					content = @Content)})
	@PutMapping("/updateSkills")
	public ResponseEntity<?> updateSkillsToProfile(@RequestBody SkillsRequest request) {

			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			User authenticatedUser = userService.getUserByEmail(email);
			Profile updateProfile = profileService.getProfileByUserId(authenticatedUser.getId());

			if(request.getSkillsLearned() == null && request.getSkillsToLearn() == null){
				return ResponseEntity.badRequest().body("Se necesita ingresar skillsLearned y/o skillsToLearn");
			}
			if (request.getSkillsLearned() != null) {
				updateProfile = profileService.updateProfileSkills("learned", updateProfile.getId(), request.getSkillsLearned());
			}
			if (request.getSkillsToLearn() != null) {
				updateProfile = profileService.updateProfileSkills("learning", updateProfile.getId(), request.getSkillsToLearn());
			}

			return ResponseEntity.ok(profileMapper.toDTO(updateProfile));

	}

	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Profile deleted successfully.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Profile not found.",
					content = @Content)})
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteProfileById(@PathVariable("userId") Long id) {
		try {
			Profile profile = profileService.getProfileByUserId(id);
			profileService.deleteProfile(profile.getId());
			return ResponseEntity.ok("Perfil eliminado exitosamente");
		} catch (ResourceNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}

package com.sheiladiz.controllers;

import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.exceptions.InvalidDataException;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/profiles")
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
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProfileDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "El perfil para este usuario ya existe.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/me")
    public ResponseEntity<ProfileDTO> createMyProfile(Authentication authentication, @Valid @RequestBody ProfileDTO profileDTO) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        Profile savedProfile = profileService.saveProfile(profileDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(profileMapper.toDTO(savedProfile));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProfileDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        Profile profile = profileService.getProfileByUser(user);
        return ResponseEntity.ok(profileMapper.toDTO(profile));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProfileDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/me")
    public ResponseEntity<ProfileDTO> updateProfile(Authentication authentication, @RequestBody ProfileDTO newProfileDTO) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);

        Profile updatedProfile = profileService.updateProfile(user.getProfile().getId(), newProfileDTO);
        return ResponseEntity.ok(profileMapper.toDTO(updatedProfile));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProfileDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Habilidad {habilidad} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/me/addSkills")
    public ResponseEntity<ProfileDTO> addSkillsToProfile(Authentication authentication, @RequestBody SkillsRequest request) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        Profile profileToUpdate = profileService.getProfileByUserId(user.getId());

        if (request.getSkillsLearned() == null && request.getSkillsToLearn() == null) {
            throw new InvalidDataException("Se necesita ingresar skillsLearned y/o skillsToLearn");
        }

        if (request.getSkillsLearned() != null) {
            profileToUpdate = profileService.addProfileSkills("learned", profileToUpdate.getId(), request.getSkillsLearned());
        }

        if (request.getSkillsToLearn() != null) {
            profileToUpdate = profileService.addProfileSkills("learning", profileToUpdate.getId(), request.getSkillsToLearn());
        }

        return ResponseEntity.ok(profileMapper.toDTO(profileToUpdate));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProfileDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Habilidad {habilidad} no encontrada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/me/updateSkills")
    public ResponseEntity<ProfileDTO> updateSkillsToProfile(Authentication authentication, @RequestBody SkillsRequest request) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        Profile profileToUpdate = profileService.getProfileByUserId(user.getId());

        if (request.getSkillsLearned() == null && request.getSkillsToLearn() == null) {
            throw new InvalidDataException("Se necesita ingresar skillsLearned y/o skillsToLearn");
        }

        if (request.getSkillsLearned() != null) {
            profileToUpdate = profileService.updateProfileSkills("learned", profileToUpdate.getId(), request.getSkillsLearned());
        }

        if (request.getSkillsToLearn() != null) {
            profileToUpdate = profileService.updateProfileSkills("learning", profileToUpdate.getId(), request.getSkillsToLearn());
        }

        return ResponseEntity.ok(profileMapper.toDTO(profileToUpdate));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil eliminado exitosamente.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario con id {userId} no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteProfileById(Authentication authentication) {
        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        profileService.deleteProfile(user.getId());
        return ResponseEntity.ok("Perfil eliminado exitosamente.");
    }
}

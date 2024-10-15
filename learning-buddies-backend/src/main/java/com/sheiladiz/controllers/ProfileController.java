package com.sheiladiz.controllers;

import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.dtos.profile.ResponseProtectedProfileDto;
import com.sheiladiz.exceptions.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.dtos.profile.RequestProfileDto;
import com.sheiladiz.models.User;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final UserService userService;

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProfileDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "El perfil para este usuario ya existe.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @PostMapping("/me")
    public ResponseEntity<ResponseProfileDto> createMyProfile(Authentication authentication,
                                                              @Valid @RequestBody RequestProfileDto requestProfileDto) {
        User user = getUser(authentication);

        ResponseProfileDto savedProfile = profileService.saveProfile(requestProfileDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProfileDto.class))}),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping("/me")
    public ResponseEntity<ResponseProfileDto> getMyProfile(Authentication authentication) {
        User user = getUser(authentication);

        ResponseProfileDto profile = profileService.getProfileByUser(user);
        return ResponseEntity.ok(profile);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(type = "array", implementation = ResponseProtectedProfileDto.class))}),
            @ApiResponse(responseCode = "404", description = "Perfiles no encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping
    public ResponseEntity<List<ResponseProtectedProfileDto>> getProfiles(
            @RequestParam(required = false) List<String> skillsLearned,
            @RequestParam(required = false) List<String> skillsToLearn) {
        List<ResponseProtectedProfileDto> profiles;
        if (skillsLearned == null && skillsToLearn == null) {
            profiles = profileService.allProtectedProfiles();
        } else {
            profiles = profileService.getProfilesBySkills(skillsLearned, skillsToLearn);
        }

        return ResponseEntity.ok(profiles);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RequestProfileDto.class))}),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @PutMapping("/me")
    public ResponseEntity<ResponseProfileDto> updateProfile(Authentication authentication,
                                                           @RequestBody RequestProfileDto newRequestProfileDto) {
        User user = getUser(authentication);

        ResponseProfileDto updatedProfile = profileService.updateProfile(user.getProfileId(), newRequestProfileDto);
        return ResponseEntity.ok(updatedProfile);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil eliminado exitosamente.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario con id {userId} no encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @DeleteMapping("/me")
    public ResponseEntity<?> deleteProfileById(Authentication authentication) {
        User user = getUser(authentication);

        profileService.deleteProfile(user.getId());
        return ResponseEntity.ok("Perfil eliminado exitosamente.");
    }

    private User getUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }
}

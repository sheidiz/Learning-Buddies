package com.sheiladiz.controllers.admin;

import com.sheiladiz.dtos.profile.RequestProfileDto;
import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.services.ProfileService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/profiles")
public class AdminProfileController {

    private final ProfileService profileService;

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProfileDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "El perfil para este usuario ya existe.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @PostMapping("/{userId}")
    public ResponseEntity<ResponseProfileDto> createMyProfile(@PathVariable("userId") String userId,
                                                              @Valid @RequestBody RequestProfileDto requestProfileDto) {
        ResponseProfileDto savedProfile = profileService.saveProfileByUserId(requestProfileDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProfile);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ResponseProfileDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<ResponseProfileDto>> getAllProfiles() {
        List<ResponseProfileDto> profiles = profileService.allProfiles();
        return ResponseEntity.ok(profiles);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProfileDto.class))}),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping("/{profileId}")
    public ResponseEntity<ResponseProfileDto> getProfileById(@PathVariable("profileId") Long profileId) {

        ResponseProfileDto profile = profileService.getProfileById(profileId);
        return ResponseEntity.ok(profile);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil eliminado exitosamente.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario con id {userId} no encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})})
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteProfileById(@PathVariable("userId") String userId) {
        profileService.deleteProfile(userId);
        return ResponseEntity.ok("Perfil eliminado exitosamente.");
    }

}

package com.sheiladiz.controllers;

import com.sheiladiz.dtos.ProfileDTO;
import com.sheiladiz.mappers.ProfileMapper;
import com.sheiladiz.models.Profile;
import com.sheiladiz.services.ProfileService;
import com.sheiladiz.services.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/profiles")
public class AdminProfileController {
    private final ProfileService profileService;
    private final UserService userService;
    private final ProfileMapper profileMapper;

    public AdminProfileController(ProfileService profileService, UserService userService, ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.userService = userService;
        this.profileMapper = profileMapper;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ProfileDTO.class))})
    })
    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        List<Profile> profiles = profileService.allProfiles();
        return ResponseEntity.ok(profileMapper.profilesToProfileDTOs(profiles));
    }
}

package com.sheiladiz.controllers.admin;

import com.sheiladiz.dtos.profile.ResponseProfileDto;
import com.sheiladiz.services.ProfileService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/profiles")
public class AdminProfileController {
    private final ProfileService profileService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ResponseProfileDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<ResponseProfileDto>> getAllProfiles() {
        List<ResponseProfileDto> profiles = profileService.allProfiles();
        return ResponseEntity.ok(profiles);
    }
}

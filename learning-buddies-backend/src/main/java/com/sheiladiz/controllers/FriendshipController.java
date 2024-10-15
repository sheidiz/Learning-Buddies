package com.sheiladiz.controllers;
/*
import com.sheiladiz.dtos.FriendshipsResponse;
import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.mappers.FriendshipMapper;
import com.sheiladiz.models.Friendship;
import com.sheiladiz.models.FriendshipStatus;
import com.sheiladiz.models.Profile;
import com.sheiladiz.services.FriendshipService;
import com.sheiladiz.services.ProfileService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final FriendshipMapper friendshipMapper;
    private final ProfileService profileService;

    public FriendshipController(FriendshipService friendshipService, FriendshipMapper friendshipMapper, ProfileService profileService) {
        this.friendshipService = friendshipService;
        this.friendshipMapper = friendshipMapper;
        this.profileService = profileService;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Solicitud enviada a {nombre} con éxito.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Los IDs deben pertenecer a diferentes perfiles.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Perfil con id {profileId} no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PostMapping("/send-request/{friendProfileId}")
    public ResponseEntity<?> sendFriendshipRequest(Authentication authentication, @PathVariable("friendProfileId") Long friendProfileId) {
        String email = authentication.getName();
        Profile currentUserProfile = profileService.getProfileByUserEmail(email);
        Profile friendProfile = profileService.getProfileById(friendProfileId);

        friendshipService.sendFriendRequest(currentUserProfile, friendProfile);

        String createdMessage = "Solicitud enviada a " + friendProfile.getName() + " con éxito.";
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = FriendshipsResponse.class))})
    })
    @GetMapping("/me")
    public ResponseEntity<FriendshipsResponse> getFriendships(Authentication authentication) {
        String email = authentication.getName();
        Profile currentUserProfile = profileService.getProfileByUserEmail(email);

        List<Profile> friends = friendshipService.getFriendsProfiles(currentUserProfile);
        List<Profile> receivedRequests = friendshipService.getPendingReceivedFriendshipProfiles(currentUserProfile);
        List<Profile> sentRequests = friendshipService.getPendingFriendshipProfiles(currentUserProfile);

        FriendshipsResponse friendshipResponse = friendshipMapper.toFriendshipResponse(friends, receivedRequests, sentRequests);
        return new ResponseEntity<>(friendshipResponse, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud aceptada a {nombre} con éxito.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Los IDs deben pertenecer a diferentes perfiles.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Perfil con id {friendProfileId} no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/me/accept-request/{friendProfileId}")
    public ResponseEntity<?> acceptFriendshipRequest(Authentication authentication, @PathVariable("friendProfileId") Long friendProfileId) {
        String email = authentication.getName();
        Profile currentUserProfile = profileService.getProfileByUserEmail(email);
        Profile friendProfile = profileService.getProfileById(friendProfileId);

        friendshipService.acceptFriendRequest(currentUserProfile, friendProfile);

        String createdMessage = "Solicitud aceptada a " + friendProfile.getName() + " con éxito.";
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amistad/Solicitud de amistad eliminada exitosamente.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil con id {friendProfileId} no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @DeleteMapping("/me/reject-request/{friendProfileId}")
    public ResponseEntity<?> rejectFriendshipRequest(Authentication authentication, @PathVariable("friendProfileId") Long friendProfileId) {
        String email = authentication.getName();
        Profile currentUserProfile = profileService.getProfileByUserEmail(email);
        Profile friendProfile = profileService.getProfileById(friendProfileId);

        Friendship friendship = friendshipService.findFriendshipBetween(friendProfile, currentUserProfile);

        friendshipService.removeFriendship(friendship);
        if (friendship.getStatus() == FriendshipStatus.ACCEPTED) {
            return ResponseEntity.ok("Amistad eliminada exitosamente");
        } else {
            return ResponseEntity.ok("Solicitud de amistad eliminada exitosamente");
        }
    }

}
*/
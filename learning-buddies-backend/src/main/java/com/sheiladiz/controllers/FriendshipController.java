package com.sheiladiz.controllers;

import com.sheiladiz.dtos.friendship.ResponseFriendshipLists;
import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.models.User;
import com.sheiladiz.services.FriendshipService;
import com.sheiladiz.services.UserService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final UserService userService;

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
        User user = getUser(authentication);

        friendshipService.sendFriendRequest(user.getProfileId(), friendProfileId);

        return new ResponseEntity<>("Solicitud enviada con éxito.", HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseFriendshipLists.class))})
    })
    @GetMapping("/me")
    public ResponseEntity<ResponseFriendshipLists> getFriendships(Authentication authentication) {
        User user = getUser(authentication);

        ResponseFriendshipLists friendshipResponse = friendshipService.getAllFriendshipsAndRequests(user.getProfileId());
        return new ResponseEntity<>(friendshipResponse, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Solicitud aceptada con éxito.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "No se puede enviar una solicitud de amistad a uno mismo.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Perfil con id {friendProfileId} no encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping("/me/accept-request/{friendProfileId}")
    public ResponseEntity<?> acceptFriendshipRequest(Authentication authentication, @PathVariable("friendProfileId") Long friendProfileId) {
        User user = getUser(authentication);

        friendshipService.acceptFriendRequest(user.getProfileId(), friendProfileId);

        String createdMessage = "Solicitud aceptada con éxito.";
        return new ResponseEntity<>(createdMessage, HttpStatus.OK);
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
        User user = getUser(authentication);

        friendshipService.rejectFriendRequest(user.getProfileId(), friendProfileId);

        String createdMessage = "Solicitud rechazada con éxito.";
        return new ResponseEntity<>(createdMessage, HttpStatus.OK);
    }

    private User getUser(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

}
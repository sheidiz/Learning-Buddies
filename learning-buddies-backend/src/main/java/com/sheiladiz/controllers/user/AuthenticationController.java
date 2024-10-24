package com.sheiladiz.controllers.user;

import com.sheiladiz.dtos.user.*;
import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.models.User;
import com.sheiladiz.services.AuthenticationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/user/authentication")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseUserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos enviados no cumplen los requisitos de la entidad.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "Email ya se encuentra registrado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @PostMapping("/register")
    public ResponseEntity<ResponseUserDto> registerUser(@Valid @RequestBody RequestRegisterDto requestRegisterDto) {
        ResponseUserDto responseUserDto = authenticationService.signUp(requestRegisterDto);
        return new ResponseEntity<>(responseUserDto, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseLoginDto.class))}),
            @ApiResponse(responseCode = "400", description = "Datos necesarios faltantes/inválidos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "401", description = "Usuario y/o contraseña inválidos.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> loginUser(@Valid @RequestBody RequestLoginDto requestLoginDto) {
        ResponseLoginDto loginResponse = authenticationService.login(requestLoginDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody RequestChangePassword requestChangePassword) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User authenticatedUser = (User) authentication.getPrincipal();

            authenticationService.changePassword(authenticatedUser, requestChangePassword.currentPassword(), requestChangePassword.newPassword());

            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al actualizar la contraseña");
        }
    }

}

package com.sheiladiz.controllers.admin;

import java.util.List;

import com.sheiladiz.dtos.user.ResponseUserDto;
import com.sheiladiz.exceptions.ErrorResponse;
import com.sheiladiz.mappers.UserMapper;
import com.sheiladiz.models.User;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.services.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = ResponseUserDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getAllUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(userMapper.usersToUserDtos(users));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseUserDto.class))}),
            @ApiResponse(responseCode = "404", description = "Usuario con id {id} no encontrado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseUserDto.class))}),
            @ApiResponse(responseCode = "404", description = "Usuario con id {id} no encontrado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable("id") Long id, @RequestBody ResponseUserDto responseUserDto) {
        User updatedUser = userService.updateUser(id, responseUserDto);
        return ResponseEntity.ok(userMapper.userToUserDto(updatedUser));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario con id {id} no encontrado",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado exitosamente.");
    }

}

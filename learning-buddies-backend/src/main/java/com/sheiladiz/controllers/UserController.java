package com.sheiladiz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.dtos.ChangePasswordRequest;
import com.sheiladiz.dtos.UserDTO;
import com.sheiladiz.exceptions.user.EmailAlreadyRegisteredException;
import com.sheiladiz.exceptions.user.InvalidUserCredentialsException;
import com.sheiladiz.exceptions.user.UserNotFoundException;
import com.sheiladiz.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping()
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> userDTOs = userService.allUsers();
		return ResponseEntity.ok(userDTOs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		try {
			UserDTO userDto = userService.getUserById(id);
			return ResponseEntity.ok(userDto);
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
		try {
			UserDTO updatedUserDto = userService.updateUser(id, userDTO);
			return ResponseEntity.ok(updatedUserDto);
		} catch (UserNotFoundException | EmailAlreadyRegisteredException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
		}
	}

	/*@PutMapping("/change-password/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
			@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
		try {
			userService.changePassword(id, changePasswordRequest);
			return ResponseEntity.ok("Contraseña actualizada correctamente");
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (InvalidUserCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
		}
	}*/

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		try {
			userService.deleteUser(id);
			return ResponseEntity.ok("User deleted successfully");
		} catch (UserNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

}

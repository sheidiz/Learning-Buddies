package com.sheiladiz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sheiladiz.services.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	/*@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {
		try {
			UserEntity registeredUser = userService.registerUser(user);
			return ResponseEntity.ok(registeredUser);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}*/

	/*@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
	    try {
	        UserEntity user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
	        return ResponseEntity.ok(user);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(401).body(e.getMessage());
	    }
	}*/
	@PostMapping("/demo")
	public String welcome() {
		return "Welcome from secure endpoint";
	}
	
}

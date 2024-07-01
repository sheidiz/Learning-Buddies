package com.sheiladiz.dtos;

public class RegisterRequest {

	String email;
	String password;
	String authProvider;

	public RegisterRequest() {

	}

	public RegisterRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
		this.authProvider = "local";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthProvider() {
		return authProvider;
	}

	public void setAuthProvider(String authProvider) {
		this.authProvider = authProvider;
	}

}

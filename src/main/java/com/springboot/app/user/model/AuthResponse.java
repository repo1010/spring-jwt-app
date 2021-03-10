package com.springboot.app.user.model;

public class AuthResponse {

	private final String jwt;

	public AuthResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

}

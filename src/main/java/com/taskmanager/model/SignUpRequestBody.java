package com.taskmanager.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignUpRequestBody {
	@JsonProperty(value = "username")
	private String username;
	@JsonProperty(value = "password")
	private String password;
	@JsonProperty(value = "email")
	private String email;
	
	private Set<String> roles;
	
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}

package com.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInRequestBody {

	@JsonProperty(value = "username")
	private String username;
	@JsonProperty(value = "password")
	private String password;
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
	@Override
	public String toString() {
		return "SignInRequestBody [username=" + username + ", password=" + password + "]";
	}
	
}

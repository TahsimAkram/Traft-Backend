package com.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageResponse {
	@JsonProperty
	private String response;
	public MessageResponse(String response) {
		this.response = response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponse() {
		return response;
	}
}

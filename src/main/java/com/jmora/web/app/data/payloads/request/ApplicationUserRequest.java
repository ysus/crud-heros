package com.jmora.web.app.data.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ApplicationUserRequest {

	@NotBlank(message = "username mustn't be empty")
	@NotNull(message = "username is required")
	private String username;	
	
	@NotBlank(message = "password mustn't be empty")
	@NotNull(message = "password is required")
	private String password;
}
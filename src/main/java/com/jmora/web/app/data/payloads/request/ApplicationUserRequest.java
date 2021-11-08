package com.jmora.web.app.data.payloads.request;

import lombok.Data;

@Data
public class ApplicationUserRequest {

	private String username;	
	private String password;
}
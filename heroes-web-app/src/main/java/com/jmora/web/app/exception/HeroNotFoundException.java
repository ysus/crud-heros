package com.jmora.web.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HeroNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5527640851346827515L;

	public HeroNotFoundException(String message) {
		super(message);
	}
	
	

}

package com.jmora.web.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 5527640851346827515L;

	public UserAlreadyExistException(String message) {
		super(message);
	}
	
	

}
package com.jmora.web.app.exception;

public class HeroNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5527640851346827515L;

	public HeroNotFoundException(String message) {
		super(message);
	}
	
	

}

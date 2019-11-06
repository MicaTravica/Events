package com.app.events.exception;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -4139095620755563053L;

	public UserNotFoundException(Long id) {
		super(String.format("User with %s id is not found!", id.toString()));
	}

}

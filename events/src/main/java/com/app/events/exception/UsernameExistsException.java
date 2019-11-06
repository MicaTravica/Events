package com.app.events.exception;

public class UsernameExistsException extends Exception {

	private static final long serialVersionUID = 6783770141061508058L;

	public UsernameExistsException() {
		super("Username exists!");
	}
}

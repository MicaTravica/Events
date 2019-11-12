package com.app.events.exception;

public class PasswordShortException extends Exception {

	private static final long serialVersionUID = -4945909219509291799L;

	public PasswordShortException() {
		super("Password must have 8 character!");
	}
}

package com.app.events.exception;

public class EmailExistsException extends Exception {

	private static final long serialVersionUID = -6476908957362998594L;

	public EmailExistsException() {
		super("Email exists");
	}
}

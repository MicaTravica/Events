package com.app.events.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = 5409679831150278252L;

	public NotFoundException(String entity) {
		super(String.format("%s with is not found!", entity));
	}
}

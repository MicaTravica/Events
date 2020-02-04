package com.app.events.exception;

public class ResourceNullNumber extends Exception {

	private static final long serialVersionUID = 5409679831150278252L;

	public ResourceNullNumber(String entity) {
		super(String.format("Sector %s must be greater than zero", entity));
	}
}

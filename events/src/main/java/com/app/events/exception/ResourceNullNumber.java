package com.app.events.exception;

public class ResourceNullNumber extends Exception {

	private static final long serialVersionUID = 5409679831150278252L;

	public ResourceNullNumber() {
		super("Sector rows and columns must be greater than zero");
	}
}

package com.app.events.exception;

public class ResourceExistsException extends Exception {

    private static final long serialVersionUID = -837116719109228941L;

    public ResourceExistsException(String exist) {
		super(String.format("%s already exists", exist));
	}
}

package com.app.events.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 5409679831150278252L;

	public ResourceNotFoundException(String entity) {
		super(String.format("%s is not found!", entity));
	}
}

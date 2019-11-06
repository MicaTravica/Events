package com.app.events.exception;

public class MediaNotFoundException extends Exception {

	private static final long serialVersionUID = 3249048879042153095L;

	public MediaNotFoundException(Long id) {
		super(String.format("Media with %s id is not found!", id.toString()));
	}

}

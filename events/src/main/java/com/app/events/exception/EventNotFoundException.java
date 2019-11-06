package com.app.events.exception;

public class EventNotFoundException extends Exception {

	private static final long serialVersionUID = -7328998933909267305L;

	public EventNotFoundException(Long id) {
		super(String.format("Event with %s id is not found!", id.toString()));
	}

}

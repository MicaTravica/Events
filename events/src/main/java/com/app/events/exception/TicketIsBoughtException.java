package com.app.events.exception;

public class TicketIsBoughtException extends Exception {

	private static final long serialVersionUID = 8830698075924627835L;

	public TicketIsBoughtException(String message) {
		super(message);
	}
}

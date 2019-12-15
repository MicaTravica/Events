package com.app.events.exception;

public class PayPalException extends Exception {

	private static final long serialVersionUID = 8830698075924627835L;

	public PayPalException(String message) {
		super(message);
	}
}
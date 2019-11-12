package com.app.events.exception;

public class ExistsException extends Exception {

	private static final long serialVersionUID = 2061228996117681789L;

	public ExistsException(String exist) {
		super(String.format("%s already exists", exist));
	}
}

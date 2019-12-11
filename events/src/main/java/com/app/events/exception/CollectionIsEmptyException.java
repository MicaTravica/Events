package com.app.events.exception;

public class CollectionIsEmptyException extends Exception {

	private static final long serialVersionUID = -2185837277751961515L;

	public CollectionIsEmptyException(String name) {
		super(String.format("You must chose one %s", name));
	}
}

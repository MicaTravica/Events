package com.app.events.exception;

public class HallMustHaveSector extends Exception {

	private static final long serialVersionUID = 2342409333915145198L;

	public HallMustHaveSector() {
		super("Hall must have sector!");
	}
}
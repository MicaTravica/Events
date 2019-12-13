package com.app.events.exception;

public class SectorIsNotInThisHallException extends Exception {

	private static final long serialVersionUID = 6733530141312836620L;

	public SectorIsNotInThisHallException() {
		super("Sector is not in chosen hall!");
	}
}

package com.app.events.exception;

public class SectorPriceListException extends Exception {

	private static final long serialVersionUID = 2342409333915145198L;

	public SectorPriceListException() {
		super("Sector must have price list!");
	}
}

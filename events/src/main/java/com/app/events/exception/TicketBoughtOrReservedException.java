package com.app.events.exception;

public class TicketBoughtOrReservedException extends Exception {
	private static final long serialVersionUID = 2342409333915145198L;

	public TicketBoughtOrReservedException() {
		super("Ticket was bought or reserved!");
	}

}

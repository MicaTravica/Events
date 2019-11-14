package com.app.events.service;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Ticket;

public interface TicketService {

	public Ticket findOne(Long id);

	public Ticket create(Ticket ticket);

	public Ticket reserveTicket(Long id) throws ResourceNotFoundException;
	public Ticket buyTicket(Long id) throws ResourceNotFoundException;

	public void delete(Long id);

}

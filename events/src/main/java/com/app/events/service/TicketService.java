package com.app.events.service;

import com.app.events.dto.TicketDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Ticket;

public interface TicketService {

	public TicketDTO findOne(Long id);

	public TicketDTO create(Ticket ticket);

	public TicketDTO reserveTicket(Long id) throws ResourceNotFoundException;
	public TicketDTO buyTicket(Long id) throws ResourceNotFoundException;

	public void delete(Long id);

}

package com.app.events.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.PriceList;
import com.app.events.model.Ticket;

public interface TicketService {

	public Ticket findOne(Long id) throws ResourceNotFoundException;

	public Ticket create(Ticket ticket) throws Exception;

	Collection<Ticket> reserveTicket(Collection<Long> ticketIDs, Long userId) throws Exception;

	public Map<String,Object> ticketPaymentCreation(Collection<Long> ticketIDs, Long userId) throws Exception;

	public Collection<Ticket> buyTickets(Collection<Long> ticketID, Long ticketUserID, String payPalPaymentId,String payPalPayerId) throws Exception;
	
	public void delete(Long id);

	public boolean ticketForEventIsSale(Long id);

	public void deleteTicketsByEventId(Long id);

	void createTickets(Set<Hall> halls, Set<PriceList> priceLists, Long eventId, boolean update) throws Exception;

	Collection<Ticket> findAllByEventId(Long eventId) throws ResourceNotFoundException;

}

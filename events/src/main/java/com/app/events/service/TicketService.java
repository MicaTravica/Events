package com.app.events.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.Hall;
import com.app.events.model.PriceList;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.model.Ticket;

public interface TicketService {

	public Ticket findOne(Long id) throws ResourceNotFoundException;

	public Collection<Ticket> findAllForNotification(int day);

	public Ticket create(Ticket ticket) throws Exception;

	Collection<Ticket> reserveTicket(Collection<Long> ticketIDs, Long userId) throws Exception;

	public Collection<Ticket> cancelReservations(Collection<Long> ticketIDs, Long userId) throws Exception;

	public Map<String,Object> ticketPaymentCreation(Collection<Long> ticketIDs, Long userId) throws Exception;

	public Collection<Ticket> buyTickets(Collection<Long> ticketID, Long ticketUserID, String payPalPaymentId,String payPalPayerId) throws Exception;
	
	public void delete(Long id);

	public boolean ticketForEventIsSale(Long id);

	public void deleteTicketsByEventId(Long id);

	void createTickets(Set<Hall> halls, Set<PriceList> priceLists, Long eventId, boolean update) throws Exception;

	ArrayList<Ticket> createTicketsForSeat(Event event, Seat seat, PriceList priceList,Date fromDate, Date toDate);

	ArrayList<Ticket> creatTicketsForParter(Event event, Sector s, int capacity, PriceList priceList, Date fromDate,
			Date toDate) throws Exception;
	
	Collection<Ticket> findAllByEventId(Long eventId) throws ResourceNotFoundException;

	Collection<Ticket> findAllReservationsByUserId(String username);

	Page<Ticket> findAllTicketsByUserId(String username, int numOfPage, int sizeOfPage);

	Collection<Ticket> findTicketsByDateAndHallAndSector(Long eventId, Long sectorId ,Date fromDate, Date toDate) throws ResourceNotFoundException;

	Double findProfitByEventId(Long eventId);

	Double findProfitByTime(Long placeId, Date fromDate, Date toDate);

	Double findAttendanceByEventId(Long eventId);

	Double findAttendanceByTime(Long placeId, Date fromDate, Date toDate);

	void cancelReservationsCron(Collection<Ticket> tickets) throws Exception;

}

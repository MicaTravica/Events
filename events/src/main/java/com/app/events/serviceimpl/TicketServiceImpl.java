package com.app.events.serviceimpl;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.TicketReservationException;
import com.app.events.model.Event;
import com.app.events.model.Hall;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.model.Ticket;
import com.app.events.model.TicketState;
import com.app.events.model.User;
import com.app.events.repository.TicketRepository;
import com.app.events.service.EventService;
import com.app.events.service.SectorCapacityService;
import com.app.events.service.TicketService;
import com.app.events.service.UserService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private SectorCapacityService sectorCapacityService;

	@Override
	public Ticket findOne(Long id) throws ResourceNotFoundException {
		return ticketRepository.findById(id)
                    .orElseThrow(
                        ()-> new ResourceNotFoundException("Ticket")
                    ); 
	}

	@Override
	public Ticket create(Ticket ticket) {	
		return ticketRepository.save(ticket);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Ticket reserveTicket(Long id, Long userId, Long ticketVersion) throws Exception {
		Ticket ticketToUpdate = findOne(id);  
		
		if( !(ticketToUpdate.getVersion() == ticketVersion && ticketToUpdate.getUser() == null))
		{
			throw new TicketReservationException("Ticket already reserved");
		}
		User user = userService.findOne(userId);
		ticketToUpdate.setTicketState(TicketState.RESERVED);
		ticketToUpdate.setUser(user);
		return ticketRepository.save(ticketToUpdate);
	}

	@Override
	public Ticket buyTicket(Long id, Long userId) throws Exception {
		Ticket ticketToUpdate = findOne(id);
		ticketToUpdate.setTicketState(TicketState.BOUGHT);
		if(ticketToUpdate.getUser().getId() == userId)
		{
			return ticketRepository.save(ticketToUpdate);
		}
		else{
			return null;
		}
	}

	@Override
	public void delete(Long id) {
		ticketRepository.deleteById(id);
	}

	@Override
	public void createTickets(Event event, Long eventId) throws Exception {
		Event savedEvent = eventService.findOne(eventId);
		ArrayList<Ticket> tickets = new ArrayList<>();
		for(Hall h : event.getHalls()) {
			for(Sector s: h.getSectors()) {
				if(s.getSeats().size() > 0) {
					for(Seat seat: s.getSeats()) {
						tickets.add(new Ticket(null, null, TicketState.AVAILABLE, null, savedEvent, seat, null, new Long(0)));
					}
				} else {
					int capacity = s.getSectorCapacities().iterator().next().getCapacity();
					SectorCapacity sc = sectorCapacityService.create(new SectorCapacity(null, new HashSet<>(), s, capacity, capacity));
					for (int i = 0; i < sc.getCapacity(); i++) {
						tickets.add(new Ticket(null, null, TicketState.AVAILABLE, null, savedEvent, null, sc, new Long(0)));
					}
				}
			}
		}
		ticketRepository.saveAll(tickets);
		
	}

}

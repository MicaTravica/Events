package com.app.events.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.events.dto.TicketDTO;
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
import com.app.events.service.PayPalService;
import com.app.events.service.SeatService;
import com.app.events.service.SectorCapacityService;
import com.app.events.service.SectorService;
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
	private SectorService sectorService;
	
	@Autowired
	private SeatService seatService;
	
	@Autowired
	private SectorCapacityService sectorCapacityService;

	@Autowired
	private PayPalService payPalService;

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
	public Map<String,Object> ticketPaymentCreation(Long id, Long userId) throws Exception{

		Ticket ticketToUpdate = findOne(id);
		if(ticketToUpdate.getTicketState().equals(TicketState.BOUGHT) && (ticketToUpdate.getUser().getId() == userId))
		{
			return null;
		}
		//:TODO add to ticket price so payment can be proceded
		return payPalService.startPayment(ticketToUpdate.getId(), 1000);
	}


	@Override
	public Ticket buyTicket(TicketDTO ticketDTO) throws Exception {
		Ticket ticketToUpdate = findOne(ticketDTO.getId());
		ticketToUpdate.setTicketState(TicketState.BOUGHT);
		if(ticketToUpdate.getUser().getId() == ticketDTO.getUserId())
		{
			boolean payed = payPalService.completedPayment(ticketDTO.getPayPalPaymentID(), ticketDTO.getPayPalPayerID());
			if(payed)
			{
				return ticketRepository.save(ticketToUpdate);
			}
			return null;
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
	public void createTickets(Set<Hall> halls, Long eventId) throws Exception {
		Event savedEvent = eventService.findOne(eventId);
		ArrayList<Ticket> tickets = new ArrayList<>();
		for(Hall h : halls) {
			for(Sector s: h.getSectors()) {
				Sector sector  = sectorService.findOne(s.getId());
				if(sector.getSeats().size() > 0) {
					for(Seat seat: seatService.findSeatFromSector(sector.getId())) {
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

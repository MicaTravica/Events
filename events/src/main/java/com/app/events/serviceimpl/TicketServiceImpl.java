package com.app.events.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.events.exception.PayPalException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.SectorCapacatyMustBePositiveNumberException;
import com.app.events.exception.TicketIsBoughtException;
import com.app.events.exception.TicketReservationException;
import com.app.events.model.Event;
import com.app.events.model.Hall;
import com.app.events.model.PriceList;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.model.Ticket;
import com.app.events.model.TicketState;
import com.app.events.model.User;
import com.app.events.repository.TicketRepository;
import com.app.events.service.EventService;
import com.app.events.service.PayPalService;
import com.app.events.service.PriceListService;
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

	@Autowired
	private PriceListService priceListService;

	@Override
	public Ticket findOne(Long id) throws ResourceNotFoundException {
		return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket"));
	}

	@Override
	public Collection<Ticket> findAllByEventId(Long eventId) throws ResourceNotFoundException {
		Collection<Ticket> tickets = ticketRepository.findAllByEventId(eventId);
		if (tickets.size() == 0) {
			throw new ResourceNotFoundException("Tickets for Event");
		}
		return tickets;
	}

	@Override
	public Ticket create(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Collection<Ticket> reserveTicket(Collection<Long> ticketIDs, Long userId) throws Exception {
		
		Collection<Ticket> retVal = new ArrayList<>();
		Collection<Ticket> reservedTickets = new ArrayList<>();
		User user = userService.findOne(userId);
		for(Long ticketID: ticketIDs) {
			Ticket ticketToUpdate = findOne(ticketID);

			if (!(ticketToUpdate.getUser() == null)) {
				throw new TicketReservationException("Ticket already reserved");
			}
			reservedTickets.add(ticketToUpdate);
		}
		for(Ticket t: reservedTickets){
			t.setTicketState(TicketState.RESERVED);
			t.setUser(user);
			retVal.add(ticketRepository.save(t));
		}
		return retVal;
	}

	@Override
	public Map<String,Object> ticketPaymentCreation(Collection<Long> ticketIDs, Long userId) throws Exception{
		
		Double amout = 0.0;
		for(Long ticketID: ticketIDs) {
			Ticket ticketToUpdate = findOne(ticketID);
			if (ticketToUpdate.getTicketState().equals(TicketState.RESERVED)
				&& ticketToUpdate.getUser().getId() != userId) {
			throw new TicketReservationException("Ticket already reserved by other user");
			}
			if(ticketToUpdate.getTicketState().equals(TicketState.BOUGHT))
			{
				throw new TicketIsBoughtException("Ticket is already bought");
			}
			amout += ticketToUpdate.getPrice();
		}
		
		return payPalService.startPayment(amout);
	}

	@Override
	public Collection<Ticket> buyTickets(Collection<Long> ticketIDs, Long ticketUserID, String payPalPaymentId,String payPalPayerId) throws Exception {
		Collection<Ticket> retVal = new ArrayList<>();
		Collection<Ticket> boughtTickets = new ArrayList<>();
		for(Long ticketID: ticketIDs){
			
			Ticket ticketToUpdate = findOne(ticketID);
			ticketToUpdate.setTicketState(TicketState.BOUGHT);
			if(ticketToUpdate.getUser() == null || 
					(ticketToUpdate.getUser().getId() == ticketUserID &&
					ticketToUpdate.getTicketState().equals(TicketState.RESERVED))
			)
			{
				User user = null;
				if(ticketToUpdate.getUser() == null)
				{
					user = userService.findOne(ticketUserID);
					ticketToUpdate.setUser(user);
				}
				boughtTickets.add(ticketToUpdate);
			}
			else {
				throw new TicketIsBoughtException("Ticket is already bought by other user");
			}
		}
		// kad je prosao sve tikete da moze da ih kupi
		// vrsi se transakcija ako prodje transakcija
		// onda za svaku kartu update da je on sada vlasnik karte
		boolean payed = payPalService.completedPayment(payPalPaymentId, payPalPayerId);
		if(!payed)
		{
			throw new PayPalException("Not enough money on card for ticket purchuse");
		}
		boughtTickets.stream().forEach(
			ticket-> {
				Ticket savedTicket = ticketRepository.save(ticket);
				retVal.add(savedTicket);
			});
		return retVal;
	}

	@Override
	public void delete(Long id) {
		ticketRepository.deleteById(id);
	}

	@Override
	public void createTickets(Set<Hall> halls, Set<PriceList> priceLists, Long eventId, boolean update)
			throws Exception {
		Event savedEvent = eventService.findOne(eventId);
		Map<Long, PriceList> priceListMap = priceLists.stream()
				.collect(Collectors.toMap(x -> x.getSector().getId(), x -> x));
		ArrayList<Ticket> tickets = new ArrayList<>();
		for (Hall h : halls) {
			for (Sector s : h.getSectors()) {
				Sector sector = sectorService.findOne(s.getId());
				PriceList priceList = priceListMap.get(sector.getId());
				PriceList savedPriceList = priceListService
						.create(new PriceList(null, priceList.getPrice(), savedEvent, sector));
				if (sector.getSeats().size() > 0) {
					for (Seat seat : seatService.findSeatFromSector(sector.getId())) {
						tickets.add(new Ticket(null, null, savedPriceList.getPrice(), TicketState.AVAILABLE, null,
								savedEvent, seat, null, new Long(0)));
					}
				} else {
					int capacity = s.getSectorCapacities().iterator().next().getCapacity();
					if (capacity < 1) {
						if (!update)
							eventService.delete(eventId);
						throw new SectorCapacatyMustBePositiveNumberException();
					}
					SectorCapacity sc = sectorCapacityService
							.create(new SectorCapacity(null, new HashSet<>(), s, capacity, capacity));
					for (int i = 0; i < sc.getCapacity(); i++) {
						tickets.add(new Ticket(null, null, savedPriceList.getPrice(), TicketState.AVAILABLE, null,
								savedEvent, null, sc, new Long(0)));
					}
				}
			}
		}
		ticketRepository.saveAll(tickets);

	}

	@Override
	public boolean ticketForEventIsSale(Long id) {
		return ticketRepository.ticketForEventIsSale(id);
	}

	@Override
	public void deleteTicketsByEventId(Long id) {
		Collection<Ticket> tickets = ticketRepository.findAllByEventId(id);
		for (Ticket ticket : tickets) {
			ticket.setEvent(null);
			ticket.setSeat(null);
		}
		ticketRepository.deleteAll(tickets);
	}

	@Override
	public Collection<Ticket> findAllReservationsByUserId(Long userId) {
		return this.ticketRepository.findAllReservationsByUserId(userId);
	}

}

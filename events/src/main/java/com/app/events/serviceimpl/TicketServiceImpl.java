package com.app.events.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.app.events.service.*;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.events.exception.PayPalException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.SectorCapacatyMustBePositiveNumberException;
import com.app.events.exception.TicketIsBoughtException;
import com.app.events.exception.TicketReservationException;
import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.Hall;
import com.app.events.model.PriceList;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.model.Ticket;
import com.app.events.model.TicketState;
import com.app.events.model.User;
import com.app.events.repository.TicketRepository;

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

	@Autowired
	private MailService mailService;

	@Override
	public Ticket findOne(Long id) throws ResourceNotFoundException {
		return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket"));
	}

	@Override
	public Collection<Ticket> findAllForNotification(int day) {
		DateTime dt = new DateTime();
		dt = dt.plusDays(day);
		return this.ticketRepository.findAllForNotification(dt.toDate(), new Date());
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
			if(t.getSectorCapacity() != null) {
				SectorCapacity sc = sectorCapacityService.findOne(t.getSectorCapacity().getId());
				sc.setFree(sc.getFree() - 1);
				sectorCapacityService.update(sc);
			}
		}

		System.out.println(ticketRepository.availableTikcketsByEventId(15L));
		return retVal;
	}

	@Override
	public Collection<Ticket> cancelReservations(Collection<Long> ticketIDs, Long userId) throws Exception {
		
		Collection<Ticket> retVal = new ArrayList<>();
		Collection<Ticket> reservedTickets = new ArrayList<>();
		User user = userService.findOne(userId);
		for(Long ticketID: ticketIDs) {
			Ticket ticketToUpdate = findOne(ticketID);
			if (	(ticketToUpdate.getUser() == null) ||
					(ticketToUpdate.getUser().getId() != user.getId())
				) {
				throw new TicketReservationException("Ticket was not reserved by this user");
			}
			if (!ticketToUpdate.getTicketState().equals(TicketState.RESERVED)) {
				throw new TicketReservationException("Ticket was not in reserved state");
			}
			// TODO: check ticket Start and End date with current date...
			reservedTickets.add(ticketToUpdate);
		}
		for(Ticket t: reservedTickets){
			t.setTicketState(TicketState.AVAILABLE);
			t.setUser(null);
			retVal.add(ticketRepository.save(t));
			if (t.getSectorCapacity() != null) {
				SectorCapacity sc = sectorCapacityService.findOne(t.getSectorCapacity().getId());
				sc.setFree(sc.getFree() + 1);
				sectorCapacityService.update(sc);
			}
		}
		return retVal;
	}
	
	@Override
	public void cancelReservationsCron(Collection<Ticket> tickets) throws Exception {
		for(Ticket t: tickets){
			t.setTicketState(TicketState.AVAILABLE);
			t.setUser(null);
			if (t.getSectorCapacity() != null) {
				SectorCapacity sc = sectorCapacityService.findOne(t.getSectorCapacity().getId());
				sc.setFree(sc.getFree() + 1);
				sectorCapacityService.update(sc);
			}
		}
	}

	@Override
	public String generateStringForQRCodeImage(Ticket t) {
		StringBuilder sb = new StringBuilder();
		sb.append("fromtDate: ");
		sb.append(t.getFromDate().toString());
		sb.append(" toDate: ");
		sb.append(t.getToDate());
		sb.append(" price: ");
		sb.append(t.getPrice());
		return sb.toString();
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
		Collection<Long> sc = new ArrayList<>();
		Set<Long> events = new HashSet<>();
		for(Long ticketID: ticketIDs){
			
			Ticket ticketToUpdate = findOne(ticketID);
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

				if(ticketToUpdate.getSectorCapacity() != null && ticketToUpdate.getTicketState() == TicketState.AVAILABLE) {
					sc.add(ticketToUpdate.getSectorCapacity().getId());
				}
				ticketToUpdate.setTicketState(TicketState.BOUGHT);
				boughtTickets.add(ticketToUpdate);
				events.add(ticketToUpdate.getEvent().getId());
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
		for (Long scId : sc) {
			SectorCapacity scu = sectorCapacityService.findOne(scId);
			scu.setFree(scu.getFree() - 1);
			sectorCapacityService.update(scu);
		}
		for(Long eId: events) {
			if(ticketRepository.availableTikcketsByEventId(eId) == 0) {
				eventService.updateEventState(eventService.findOne(eId), EventState.SOLD_OUT);
			}
		}
		this.mailService.ticketsBought((ArrayList<Ticket>) retVal);
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
				PriceList savedPriceList;
				try {
					savedPriceList = priceListService
							.create(new PriceList(null, priceList.getPrice(), savedEvent, sector));
				} catch (Exception e) {
					eventService.delete(eventId);
					throw e;
				}

				Date toDate = savedEvent.getToDate();
				Date fromDate = savedEvent.getFromDate();
				
				if (sector.getSectorRows() > 0 && sector.getSectorColumns() > 0) {
					for (Seat seat : seatService.findSeatFromSector(sector.getId())) {
						this.createTicketsForSeat
							(savedEvent,seat, savedPriceList, fromDate, toDate)
								.forEach(x -> tickets.add(x));
					}
				} else {
					// parter
					int capacity = s.getSectorCapacities().iterator().next().getCapacity();
					if (capacity < 1) {
						if (!update)
							eventService.delete(eventId);
						throw new SectorCapacatyMustBePositiveNumberException();
					}
					this.creatTicketsForParter(savedEvent, s, capacity, savedPriceList, fromDate, toDate)
						.forEach(t-> tickets.add(t));
				}
			}
		}
		ticketRepository.saveAll(tickets);
	}

	@Override
	public ArrayList<Ticket> createTicketsForSeat(
			Event event, Seat seat, PriceList priceList,Date fromDate, Date toDate) {

		ArrayList<Ticket> tickets = new ArrayList<>();
		DateTime startDate = new DateTime(fromDate);
		DateTime endDate = new DateTime(toDate);
		int nubmerOfDays = this.calculateNubmerOfDaysBetween(startDate, endDate);
		// ako traje samo 1 dan
		if (nubmerOfDays == 0) {
			tickets.add(new Ticket(null, null, priceList.getPrice(), fromDate, toDate,
					TicketState.AVAILABLE, null, event, seat, null, new Long(0)));
		} else {
			if(endDate.getHourOfDay() > startDate.getHourOfDay()) {
				for (int i = 0; i <= nubmerOfDays; i++) {
					DateTime startDate1 = new DateTime(fromDate);
					startDate1 = startDate1.plusDays(i);
					DateTime endDate1 = new DateTime(toDate);
					endDate1 = endDate1.minusDays((nubmerOfDays - i));
					tickets.add(new Ticket(null, null, priceList.getPrice(), startDate1.toDate(),
							endDate1.toDate(), TicketState.AVAILABLE, null, event, seat, null,
							new Long(0)));
				}
			}
			else {
				for (int i = 0; i < nubmerOfDays; i++) {
					DateTime startDate1 = new DateTime(fromDate);
					startDate1 = startDate1.plusDays(i);
					DateTime endDate1 = new DateTime(toDate);
					endDate1 = endDate1.minusDays((nubmerOfDays - i - 1));
					tickets.add(new Ticket(null, null, priceList.getPrice(), startDate1.toDate(),
							endDate1.toDate(), TicketState.AVAILABLE, null, event, seat, null,
							new Long(0)));
				}
			}
		}
		return tickets;
	}

	@Override
	public ArrayList<Ticket> creatTicketsForParter(Event event, Sector s, int capacity, PriceList priceList,
			Date fromDate, Date toDate) throws Exception {
		ArrayList<Ticket> tickets = new ArrayList<>();
		DateTime startDate = new DateTime(fromDate);
		DateTime endDate = new DateTime(toDate);
		int nubmerOfDays = this.calculateNubmerOfDaysBetween(startDate, endDate);
		if (nubmerOfDays == 0) {
			SectorCapacity sc = sectorCapacityService
					.create(new SectorCapacity(null, new HashSet<>(), s, capacity, capacity));
			for (int i = 0; i < sc.getCapacity(); i++) {
				tickets.add(new Ticket(null, null, priceList.getPrice(), fromDate, toDate, TicketState.AVAILABLE, null,
						event, null, sc, new Long(0)));
			}
		} else {
			if (endDate.getHourOfDay() > startDate.getHourOfDay()) {
				for (int j = 0; j <= nubmerOfDays; j++) {
					DateTime startDate1 = new DateTime(fromDate);
					startDate1 = startDate1.plusDays(j);
					DateTime endDate1 = new DateTime(toDate);
					endDate1 = endDate1.minusDays((nubmerOfDays - j));
					SectorCapacity sc = sectorCapacityService
							.create(new SectorCapacity(null, new HashSet<>(), s, capacity, capacity));
					for (int i = 0; i < sc.getCapacity(); i++) {
						tickets.add(new Ticket(null, null, priceList.getPrice(), startDate1.toDate(), endDate1.toDate(),
								TicketState.AVAILABLE, null, event, null, sc, new Long(0)));
					}
				}
			} else {
				for (int i = 0; i < nubmerOfDays; i++) {
					DateTime startDate1 = new DateTime(fromDate);
					startDate1 = startDate1.plusDays(i);
					DateTime endDate1 = new DateTime(toDate);
					endDate1 = endDate1.minusDays((nubmerOfDays - i - 1));
					SectorCapacity sc = sectorCapacityService
							.create(new SectorCapacity(null, new HashSet<>(), s, capacity, capacity));
					for (int j = 0; j < sc.getCapacity(); j++) {
						tickets.add(new Ticket(null, null, priceList.getPrice(), startDate1.toDate(), endDate1.toDate(),
								TicketState.AVAILABLE, null, event, null, sc, new Long(0)));
					}
				}
			}
		}
		return tickets;
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
	public Collection<Ticket> findAllReservationsByUserId(String username) {
		return this.ticketRepository.findAllReservationsByUserId(username);
	}
	
	@Override
	public Page<Ticket> findAllTicketsByUserId(String username, int numOfPage, int sizeOfPage) {
		Pageable pageable = PageRequest.of(numOfPage, sizeOfPage,
					Sort.by("event.fromDate").descending());
		return this.ticketRepository.findAllTicketsByUserId(username, pageable);
	}

	@Override
	public Collection<Ticket> findTicketsByDateAndHallAndSector(Long eventId, Long sectorId, Date fromDate, Date toDate)
			throws ResourceNotFoundException {
		Collection<Ticket> retVal = new ArrayList<>();
		this.ticketRepository.findTicketsByDatesAndEventId(eventId, fromDate, toDate)
				.forEach( t ->{
					if(t.getSeat() != null)
					{
						if (t.getSeat().getSector().getId() == sectorId) {
							retVal.add(t);
						}
					}
					else if (t.getSectorCapacity() != null) {
						if (t.getSectorCapacity().getSector().getId() == sectorId) { 
							retVal.add(t);
						}
					}
				});
		if(retVal.size() < 1) {
			throw new ResourceNotFoundException("Desired tickets");
		}
		return retVal;
	}

	int calculateNubmerOfDaysBetween(DateTime startDate, DateTime endDate) {
		DateTime d = startDate.minusHours(startDate.getHourOfDay());
		d = d.minusMinutes(d.getMinuteOfHour());
		DateTime d2 = endDate.minusHours(endDate.getHourOfDay());
		d2 = d2.minusMinutes(d2.getMinuteOfHour());
		return Days.daysBetween(d, d2).getDays();
	}

	@Override
	public Double findProfitByEventId(Long eventId) {
		return ticketRepository.findProfitByEventId(eventId);
	}

	@Override
	public Double findProfitByTime(Long placeId, Date fromDate, Date toDate) {
		return ticketRepository.findProfitByTime(placeId, fromDate, toDate);
	}

	@Override
	public Double findAttendanceByEventId(Long eventId) {
		return ticketRepository.findAttendanceByEventId(eventId);
	}

	@Override
	public Double findAttendanceByTime(Long placeId, Date fromDate, Date toDate) {
		return ticketRepository.findAttendanceByTime(placeId, fromDate, toDate);
	}

}

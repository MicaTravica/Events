package com.app.events.serviceimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;

import com.app.events.model.EventState;
import com.app.events.model.Ticket;
import com.app.events.service.CronService;
import com.app.events.service.EventService;
import com.app.events.service.MailService;
import com.app.events.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * CronServiceImpl
 */
@Component
public class CronServiceImpl implements CronService {

	@Autowired
	private EventService eventService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	MailService mailService;

	@Scheduled(cron = "0 0 12 * * ?")
	@Override
	public void notifyUsersForReservations() {
		HashMap<String, ArrayList<Ticket>> notifList = new HashMap<>();
		Collection<Ticket> tickets = ticketService.findAllForNotification(4);
		tickets.forEach(t -> {
			String email = t.getUser().getEmail();
			if (!notifList.keySet().contains(email)) {
				notifList.put(email, new ArrayList<>());
			}
			notifList.get(t.getUser().getEmail()).add(t);
		});
		for (String email : notifList.keySet()) {
			try {
				mailService.buyReservedTickets(email, notifList.get(email));
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Scheduled(cron = "0 0 12 * * ?")
	@Override
	public void cancleReservations() throws Exception {
		Collection<Ticket> tickets = ticketService.findAllForNotification(3);
		ticketService.cancelReservationsCron(tickets);
	}

	// svakog dana u 12:00 proveri za sve nezavrsene evente da li su se zavrsili
	@Scheduled(cron = "0 0 12 * * ?")
	@Override
	public void markEventAsFinished() throws Exception {
		Date d = new Date();
		eventService.findAllNotFinished().forEach(e -> {
			if (e.getToDate().before(d)) {
				eventService.updateEventState(e, EventState.FINISHED);
			}
		});
	}
}
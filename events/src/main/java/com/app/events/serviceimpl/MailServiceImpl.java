package com.app.events.serviceimpl;

import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.app.events.service.CloudinaryService;
import com.app.events.service.QRCodeService;
import com.app.events.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.events.model.Ticket;
import com.app.events.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Async
	@Override
	public void newUser(String email, String token) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String message = "<html><head><meta charset=\"UTF-8\"></head>" + "<body><h3>Events app - Wellcome!</h3><br>"
				+ "<div><p>You can verify your email "
				+ "<a target=\"_blank\" href = \"http://localhost:8080/api/user/verify/" + token
				+ "\"><u>here</u></a>!.</p></div></body></html>";
		mmHelper.setText(message, true);
		mmHelper.setTo(email);
		mmHelper.setSubject("Events app - Wellcome!");
		mailSender.send(mimeMessage);
	}

	@Async
	@Override
	public void changeEmail(String email) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String message = "<html><head><meta charset=\"UTF-8\"></head>" + "<body><h3>Events app - verify email!</h3><br>"
				+ "<div><p>You can verify your new email "
				+ "<a target=\"_blank\" href = \"http://localhost:8080/api/email/verify\"><u>here</u></a> "
				+ " !.</p></div></body></html>";
		mmHelper.setText(message, true);
		mmHelper.setTo(email);
		mmHelper.setSubject("Events app - verify email!");
		mailSender.send(mimeMessage);
	}

	@Async
	@Override
	public void ticketsReserved(ArrayList<Ticket> tickets) throws Exception {
		String email = tickets.get(0).getUser().getEmail();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String message = "<html><head><meta charset=\"UTF-8\"></head>"
				+ "<body><h3>Events app - your reservations!</h3><br>" + "<div><p>You reserve " + tickets.size()
				+ " tickets. If you want to buy them, you must do so no "
				+ "later than 3 days days before the event starts.<br>";
		for (Ticket ticket : tickets) {
			String ticketst = "<hr>Event name: " + ticket.getEvent().getName() + "<br>";
			if (ticket.getSeat() != null) {
				ticketst += "Place: " + ticket.getSeat().getSector().getHall().getPlace().getName() + ", "
						+ ticket.getSeat().getSector().getHall().getPlace().getAddress() + "<br>";
				ticketst += "Hall: " + ticket.getSeat().getSector().getHall().getName() + "<br>";
				ticketst += "Sector: " + ticket.getSeat().getSector().getName() + "<br>";
				ticketst += "Seat: " + ticket.getSeat().getSeatRow() + "|" + ticket.getSeat().getSeatColumn() + "<br>";
			} else {
				ticketst += "Place: " + ticket.getSectorCapacity().getSector().getHall().getPlace().getName() + ", "
						+ ticket.getSectorCapacity().getSector().getHall().getPlace().getAddress() + "<br>";
				ticketst += "Hall: " + ticket.getSectorCapacity().getSector().getHall().getName() + "<br>";
				ticketst += "Sector: " + ticket.getSectorCapacity().getSector().getName() + "<br>";
			}
			message += ticketst;
		}
		message += "</p></div></body></html>";
		mmHelper.setText(message, true);
		mmHelper.setTo(email);
		mmHelper.setSubject("Events app - your reservations!");
		mailSender.send(mimeMessage);
	}

	@Async
	@Override
	public void ticketsBought(ArrayList<Ticket> tickets) throws Exception {
		String email = tickets.get(0).getUser().getEmail();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String message = "<html><head><meta charset=\"UTF-8\"></head>" + "<body><h3>Events app - your tickets!</h3><br>"
				+ "<div><p>You bought " + tickets.size() + " tickets.<br>";
		for (Ticket ticket : tickets) {
			String ticketst = "<hr>Event name: " + ticket.getEvent().getName() + "<br>";
			ticketst += "<img href=\"" + ticket.getBarCode() + "\" height='150' width='150' >";
			if (ticket.getSeat() != null) {
				ticketst += "Place: " + ticket.getSeat().getSector().getHall().getPlace().getName() + ", "
						+ ticket.getSeat().getSector().getHall().getPlace().getAddress() + "<br>";
				ticketst += "Hall: " + ticket.getSeat().getSector().getHall().getName() + "<br>";
				ticketst += "Sector: " + ticket.getSeat().getSector().getName() + "<br>";
				ticketst += "Seat: " + ticket.getSeat().getSeatRow() + "|" + ticket.getSeat().getSeatColumn() + "<br>";
			} else {
				ticketst += "Place: " + ticket.getSectorCapacity().getSector().getHall().getPlace().getName() + ", "
						+ ticket.getSectorCapacity().getSector().getHall().getPlace().getAddress() + "<br>";
				ticketst += "Hall: " + ticket.getSectorCapacity().getSector().getHall().getName() + "<br>";
				ticketst += "Sector: " + ticket.getSectorCapacity().getSector().getName() + "<br>";
			}
			message += ticketst;
		}
		message += "</p></div></body></html>";
		mmHelper.setText(message, true);
		mmHelper.setTo(email);
		mmHelper.setSubject("Events app - your tickets!");
		mailSender.send(mimeMessage);
	}

	@Async
	@Override
	public void buyReservedTickets(String email, ArrayList<Ticket> tickets) throws MessagingException {
		// String email = ticket.getUser().getEmail();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mmHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		String message = "<html><head><meta charset=\"UTF-8\"></head>"
				+ "<body><h3>Events app - your reservation!</h3><br>"
				+ "<div><p>If you want to buy ticket, you must do so no later than 3 days days before "
				+ "the event starts. Your ticket:</p> <br>";

		for(Ticket ticket: tickets) {
			String ticketst = "<div><hr>Event name: " + ticket.getEvent().getName() + "<br>";
			if (ticket.getSeat() != null) {
				ticketst += "Place: " + ticket.getSeat().getSector().getHall().getPlace().getName() + ", "
						+ ticket.getSeat().getSector().getHall().getPlace().getAddress() + "<br>";
				ticketst += "Hall: " + ticket.getSeat().getSector().getHall().getName() + "<br>";
				ticketst += "Sector: " + ticket.getSeat().getSector().getName() + "<br>";
				ticketst += "Seat: " + ticket.getSeat().getSeatRow() + "|" + ticket.getSeat().getSeatColumn() + "<br>";
			} else {
				ticketst += "Place: " + ticket.getSectorCapacity().getSector().getHall().getPlace().getName() + ", "
						+ ticket.getSectorCapacity().getSector().getHall().getPlace().getAddress() + "<br>";
				ticketst += "Hall: " + ticket.getSectorCapacity().getSector().getHall().getName() + "<br>";
				ticketst += "Sector: " + ticket.getSectorCapacity().getSector().getName() + "<br>";
			}
			message += ticketst;
			message += "</div><br>";
		}
		message += "</div></body></html>";
		mmHelper.setText(message, true);
		mmHelper.setTo(email);
		mmHelper.setSubject("Events app - your reservations!");
		mailSender.send(mimeMessage);
	}
}

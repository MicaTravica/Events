package com.app.events.service;

import java.util.ArrayList;

import javax.mail.MessagingException;

import com.app.events.model.Ticket;

public interface MailService {

	void newUser(String email, String token) throws MessagingException;

	void changeEmail(String email) throws MessagingException;

	void ticketsReserved(ArrayList<Ticket> tickets) throws MessagingException;

	void ticketsBought(ArrayList<Ticket> tickets) throws MessagingException;

	void buyReservedTickets(Ticket ticket) throws MessagingException;

}

package com.app.events.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.dto.TicketDTO;
import com.app.events.model.Ticket;
import com.app.events.service.TicketService;

@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@GetMapping(value = "/api/tickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> getTicket(@PathVariable("id") Long id) {
		TicketDTO ticket = ticketService.findOne(id);
		if (ticket == null) {
			return new ResponseEntity<TicketDTO>(HttpStatus.NOT_FOUND);
		}
		System.out.println(ticket.toString());
		return new ResponseEntity<TicketDTO>(ticket, HttpStatus.OK);
	}

	@PostMapping(value = "/api/tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> createTicket(Ticket ticket) throws Exception {

		try {
			TicketDTO savedTicket = ticketService.create(ticket);
			return new ResponseEntity<TicketDTO>(savedTicket, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<TicketDTO>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/api/reserveTicket/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> reserveTicket(@PathVariable("id") Long id) throws Exception {
		TicketDTO updatedTicket = ticketService.reserveTicket(id);
		if (updatedTicket == null) {
			return new ResponseEntity<TicketDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<TicketDTO>(updatedTicket, HttpStatus.OK);
	}

	@PutMapping(value = "/api/buyTicket/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> buyTicket(@PathVariable("id") Long id) throws Exception {
		TicketDTO updatedTicket = ticketService.buyTicket(id);
		if (updatedTicket == null) {
			return new ResponseEntity<TicketDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<TicketDTO>(updatedTicket, HttpStatus.OK);
	}

	@PutMapping(value = "/api/tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> updateTicket(Ticket ticket) throws Exception {
		TicketDTO updatedTicket = ticketService.update(ticket);
		if (updatedTicket == null) {
			return new ResponseEntity<TicketDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<TicketDTO>(updatedTicket, HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/tickets/{id}")
	public ResponseEntity<Ticket> deleteTicket(@PathVariable("id") Long id) {
		ticketService.delete(id);
		return new ResponseEntity<Ticket>(HttpStatus.NO_CONTENT);
	}

}

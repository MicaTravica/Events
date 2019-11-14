package com.app.events.controller;

import com.app.events.dto.TicketDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.TicketMapper;
import com.app.events.model.Ticket;
import com.app.events.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {

	@Autowired
	private TicketService ticketService;

	@GetMapping(value = "/api/tickets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> getTicket(@PathVariable("id") Long id) throws ResourceNotFoundException {
		Ticket ticket = ticketService.findOne(id);
		return new ResponseEntity<>(TicketMapper.toDTO(ticket), HttpStatus.OK);
	}

	// @PostMapping(value = "/api/tickets", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	// public ResponseEntity<TicketDTO> createTicket(TicketDTO ticketDTO) throws Exception {

	// 	try {
	// 		TicketDTO savedTicket = ticketService.create(ticket);
	// 		return new ResponseEntity<TicketDTO>(savedTicket, HttpStatus.CREATED);
	// 	} catch (Exception e) {
	// 		return new ResponseEntity<TicketDTO>(HttpStatus.BAD_REQUEST);
	// 	}
	// }

	@PutMapping(value = "/api/reserveTicket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> reserveTicket(@RequestBody TicketDTO ticketDTO) throws Exception {
		Ticket updatedTicket = ticketService.reserveTicket(ticketDTO.getId(), ticketDTO.getUserId());
		return new ResponseEntity<>(TicketMapper.toDTO(updatedTicket), HttpStatus.OK);
	}

	@PutMapping(value = "/api/buyTicket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketDTO> buyTicket(@RequestBody TicketDTO ticketDTO) throws Exception {
		Ticket updatedTicket = ticketService.buyTicket(ticketDTO.getId(), ticketDTO.getUserId());
		return new ResponseEntity<>(TicketMapper.toDTO(updatedTicket), HttpStatus.OK);
	}

	@DeleteMapping(value = "/api/tickets/{id}")
	public ResponseEntity<Ticket> deleteTicket(@PathVariable("id") Long id) {
		ticketService.delete(id);
		return new ResponseEntity<Ticket>(HttpStatus.NO_CONTENT);
	}

}

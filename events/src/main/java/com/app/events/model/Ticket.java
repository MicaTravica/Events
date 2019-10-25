package com.app.events.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

	private Long id;
	private String barCode;
	private TicketState ticketState;
	private User user;
	private Event event;
	private Seat seat;
	
}

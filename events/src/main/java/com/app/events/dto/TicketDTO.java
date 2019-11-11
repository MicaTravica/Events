package com.app.events.dto;

import com.app.events.model.Ticket;
import com.app.events.model.TicketState;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TicketDTO {
	
	private Long id;
	private String barCode;
	private TicketState ticketState;
	
	private UserDTO user;
	private EventDTO event;
	private SeatDTO seat;
	private SectorCapacityDTO sectorCapacity;
	
	public TicketDTO(Ticket ticket) {
		this.id = ticket.getId();
		this.barCode = ticket.getBarCode();
		this.ticketState = ticket.getTicketState();
		
		this.user = new UserDTO(ticket.getUser());
		this.event = new EventDTO(ticket.getEvent());
		this.seat = new SeatDTO(ticket.getSeat());
		this.sectorCapacity = new SectorCapacityDTO(ticket.getSectorCapacity());
	}

	public Ticket toTicket() {
		Ticket ticket = new Ticket();
		
		ticket.setId(this.getId());
		ticket.setBarCode(this.getBarCode());
		ticket.setTicketState(this.getTicketState());
		
		// ticket.setUser(this.getUser().toUser());
		ticket.setEvent(this.getEvent().toEvent());
		ticket.setSeat(this.getSeat().toSeat());
		ticket.setSectorCapacity(this.getSectorCapacity().toSectorCapacity());
		return ticket;
	}

	// treba da se doradi za null---> nego da vraca normalne vrednosti
	public Ticket toSimpleTicket() {
		return new Ticket(
						this.getId(),
						this.getBarCode(),
						this.getTicketState(),
						null,
						null,
						null,
						null
					);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public TicketState getTicketState() {
		return ticketState;
	}
	public void setTicketState(TicketState ticketState) {
		this.ticketState = ticketState;
	}


	
	
	

}

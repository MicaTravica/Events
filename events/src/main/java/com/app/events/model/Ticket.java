package com.app.events.model;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String barCode;
	
	@Version
	private Long version;

	@Enumerated(EnumType.STRING)
	private TicketState ticketState;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="event_id", referencedColumnName="id")
	private Event event;
	
	@ManyToOne
	@JoinColumn(name="seat_id", referencedColumnName="id")
	private Seat seat;
	
	@ManyToOne
	@JoinColumn(name="sector_capacity_id", referencedColumnName="id")
	private SectorCapacity sectorCapacity;

	public Ticket(Long id, TicketState ticketState, User user) {
		this.id = id;
		this.ticketState = ticketState;
		this.user = user;
	}
	
}

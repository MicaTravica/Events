package com.app.events.dto;

import com.app.events.model.TicketState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
	
	private Long id;
	private String barCode;
	private TicketState ticketState;
	private Long userId;

	private String sectorName;
	private String HallName;
	private int seatRow;
	private int seatColumn;

	private EventDTO event;


}

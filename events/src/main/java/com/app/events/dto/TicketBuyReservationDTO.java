package com.app.events.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TicketBuyReservationDTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TicketBuyReservationDTO {

    private String payPalPaymentID;
	private String payPalToken;
    private String payPalPayerID;
    private Long userId;
    private Collection<Long> ticketIDs;
}
package com.app.events.mapper;

import com.app.events.dto.TicketDTO;
import com.app.events.model.Ticket;
import com.app.events.model.User;

public class TicketMapper {

    public static TicketDTO toDTO(Ticket ticket) {
        // karta u parteru
        if(ticket.getSeat() == null){
            return new TicketDTO(
                ticket.getId(), ticket.getBarCode(),
                ticket.getTicketState(),
                ticket.getUser() == null ? null: ticket.getUser().getId(),
                ticket.getVersion(),
                ticket.getSectorCapacity().getSector().getName(),
                ticket.getSectorCapacity().getSector().getHall().getName(),
                -1,
                -1,
                EventMapper.toDTO(ticket.getEvent()),
                ticket.getFromDate(), ticket.getToDate()
            );
        }
        else{
            return new TicketDTO(
            ticket.getId(), ticket.getBarCode(),
            ticket.getTicketState(),
            ticket.getUser() == null ? null: ticket.getUser().getId(),
            ticket.getVersion(),
            ticket.getSeat().getSector().getName(),
            ticket.getSeat().getSector().getHall().getName(),
            ticket.getSeat().getSeatRow(),
            ticket.getSeat().getSeatColumn(),
            EventMapper.toDTO(ticket.getEvent()),
            ticket.getFromDate(), ticket.getToDate()
        );
        }
    }
    
    public static Ticket toTicket(TicketDTO ticketDTO) {
        return new Ticket(
            ticketDTO.getId(),
            ticketDTO.getTicketState(),
            new User(ticketDTO.getUserId()),
            ticketDTO.getVersion()
        );
    }
    
}

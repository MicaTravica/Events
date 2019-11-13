package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.dto.TicketDTO;
import com.app.events.mapper.TicketMapper;
import com.app.events.model.Ticket;
import com.app.events.repository.TicketRepository;
import com.app.events.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public TicketDTO findOne(Long id) {

		Ticket ticket = ticketRepository.findById(id).get();
		TicketDTO TicketDTO = TicketMapper.toDTO(ticket);

		return TicketDTO;
	}

	@Override
	public TicketDTO create(Ticket ticket) {

		Ticket newTicket = ticketRepository.save(ticket);
		TicketDTO newTicketDTO = TicketMapper.toDTO(newTicket);

		return newTicketDTO;
	}

	@Override
	public TicketDTO update(Ticket ticket) throws RuntimeException {
		Ticket ticketToUpdate = ticketRepository.findById(ticket.getId()).get();
		if (ticketToUpdate == null) {
			throw new RuntimeException("Not found."); // custom exception here!
		}

		ticketToUpdate.setBarCode(ticket.getBarCode());
		ticketToUpdate.setTicketState(ticket.getTicketState());
		ticketToUpdate.setUser(ticket.getUser());
		ticketToUpdate.setEvent(ticket.getEvent());
		ticketToUpdate.setSeat(ticket.getSeat());
		ticketToUpdate.setSectorCapacity(ticket.getSectorCapacity());

		Ticket updatedTicket = ticketRepository.save(ticketToUpdate);
		TicketDTO updatedTicketDTO = TicketMapper.toDTO(updatedTicket);

		return updatedTicketDTO;
	}

	@Override
	public void delete(Long id) {
		ticketRepository.deleteById(id);
	}

}

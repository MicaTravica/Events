package com.app.events.serviceimpl;

import com.app.events.dto.TicketDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.TicketMapper;
import com.app.events.model.Ticket;
import com.app.events.model.TicketState;
import com.app.events.repository.TicketRepository;
import com.app.events.service.TicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public TicketDTO reserveTicket(Long id) throws ResourceNotFoundException {
		Ticket ticketToUpdate = ticketRepository.findById(id).get();
		if (ticketToUpdate == null) {
			throw new ResourceNotFoundException("Ticket");
		}

		ticketToUpdate.setTicketState(TicketState.RESERVED);
		//ticketToUpdate.setUser(user);

		Ticket updatedTicket = ticketRepository.save(ticketToUpdate);
		TicketDTO updatedTicketDTO = TicketMapper.toDTO(updatedTicket);

		return updatedTicketDTO;
	}

	@Override
	public TicketDTO buyTicket(Long id) throws ResourceNotFoundException {
		Ticket ticketToUpdate = ticketRepository.findById(id).get();
		if (ticketToUpdate == null) {
			throw new ResourceNotFoundException("Ticket");
		}

		ticketToUpdate.setTicketState(TicketState.BOUGHT);
		//ticketToUpdate.setUser(user);

		Ticket updatedTicket = ticketRepository.save(ticketToUpdate);
		TicketDTO updatedTicketDTO = TicketMapper.toDTO(updatedTicket);

		return updatedTicketDTO;
	}

	@Override
	public void delete(Long id) {
		ticketRepository.deleteById(id);
	}

}

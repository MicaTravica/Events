package com.app.events.serviceimpl;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Ticket;
import com.app.events.model.TicketState;
import com.app.events.model.User;
import com.app.events.repository.TicketRepository;
import com.app.events.service.TicketService;
import com.app.events.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserService userService;

	@Override
	public Ticket findOne(Long id) throws ResourceNotFoundException {
		return ticketRepository.findById(id)
                    .orElseThrow(
                        ()-> new ResourceNotFoundException("Ticket")
                    ); 
	}

	@Override
	public Ticket create(Ticket ticket) {	
		return ticketRepository.save(ticket);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Ticket reserveTicket(Long id, Long userId) throws Exception {
		Ticket ticketToUpdate = findOne(id);
		User user = userService.findOne(userId);
		ticketToUpdate.setTicketState(TicketState.RESERVED);
		ticketToUpdate.setUser(user);
		return ticketRepository.save(ticketToUpdate);
	}

	@Override
	public Ticket buyTicket(Long id, Long userId) throws Exception {
		Ticket ticketToUpdate = findOne(id);
		ticketToUpdate.setTicketState(TicketState.BOUGHT);
		if(ticketToUpdate.getUser().getId() == userId)
		{
			return ticketRepository.save(ticketToUpdate);
		}
		else{
			return null;
		}
	}

	@Override
	public void delete(Long id) {
		ticketRepository.deleteById(id);
	}

}

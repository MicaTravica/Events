package com.app.events.service;

import com.app.events.dto.SeatDTO;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Seat;

public interface SeatService {

	public Seat findOne(Long id) throws ResourceNotFoundException;

	public Seat create(Seat seat) throws ResourceNotFoundException, ResourceExistsException;
	
	public Seat update(Seat seat) throws ResourceExistsException, ResourceNotFoundException;

	public void delete(Long id);

	public boolean checkSeatFieldsAvailability(Seat seat);

	public Seat prepareSeatFields(Seat toUpdate, Seat newSeat);

}

package com.app.events.service;

import java.util.Collection;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Seat;

public interface SeatService {

	public Seat findOne(Long id) throws ResourceNotFoundException;

	public Seat create(Seat seat) throws Exception;
	
	public Seat update(Seat seat) throws Exception;

	public void delete(Long id);

	public boolean checkSeatFieldsAvailability(Seat seat);

	public Seat prepareSeatFields(Seat toUpdate, Seat newSeat);

	Collection<Seat> findSeatFromSector(Long id);

}

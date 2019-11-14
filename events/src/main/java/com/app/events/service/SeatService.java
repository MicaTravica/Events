package com.app.events.service;

import com.app.events.dto.SeatDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Seat;

public interface SeatService {

	public SeatDTO findOne(Long id);

	public Seat create(Seat seat) throws ResourceNotFoundException;
	
	public Seat update(Seat seat);

	public void delete(Long id);
}

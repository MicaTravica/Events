package com.app.events.service;

import com.app.events.dto.SeatDTO;
import com.app.events.model.Seat;

public interface SeatService {

	public SeatDTO findOne(Long id);

	public SeatDTO create(Seat seat);
	
	public SeatDTO update(Seat seat);

	public void delete(Long id);
}

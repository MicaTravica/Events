package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.dto.SeatDTO;
import com.app.events.model.Seat;
import com.app.events.repository.SeatRepository;
import com.app.events.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	private SeatRepository seatRepository;

	@Override
	public SeatDTO findOne(Long id) {

		Seat seat = seatRepository.findById(id).get();
		SeatDTO seatDTO = new SeatDTO(seat);

		return seatDTO;
	}

}

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

	@Override
	public SeatDTO create(Seat seat) {

		Seat newSeat = seatRepository.save(seat);
		SeatDTO newSeatDTO = new SeatDTO(newSeat);

		return newSeatDTO;
	}

	@Override
	public SeatDTO update(Seat seat) throws RuntimeException {
		 Seat seatToUpdate = seatRepository.findById(seat.getId()).get();
	     if (seatToUpdate == null) { 
	    	 throw new RuntimeException("Not found."); // custom exception here!
	     }
	     
	     seatToUpdate.setSeatColumn(seat.getSeatColumn());
	     seatToUpdate.setSeatRow(seat.getSeatRow());
	     seatToUpdate.setSector(seat.getSector());
	     
	     Seat updatedSeat = seatRepository.save(seatToUpdate);
	     SeatDTO updatedSeatDTO = new SeatDTO(updatedSeat);
	        
	     return updatedSeatDTO;
	}

	@Override
	public void delete(Long id) {
		seatRepository.deleteById(id);
	}

}

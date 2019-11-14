package com.app.events.serviceimpl;

import com.app.events.dto.SeatDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.SeatMapper;
import com.app.events.model.Seat;
import com.app.events.repository.SeatRepository;
import com.app.events.service.SeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	private SeatRepository seatRepository;

	@Override
	public SeatDTO findOne(Long id) {

		Seat seat = seatRepository.findById(id).get();
		SeatDTO seatDTO = SeatMapper.toDTO(seat);

		return seatDTO;
	}

	@Override
	public Seat create(Seat seat) throws ResourceNotFoundException {

		if(seat.getId() != null){
            throw new ResourceNotFoundException("Seat");
        }

		Seat newSeat = seatRepository.save(seat);
		return newSeat;
	}

	@Override
	public Seat update(Seat seat) throws RuntimeException {
		 Seat seatToUpdate = seatRepository.findById(seat.getId()).get();
	     if (seatToUpdate == null) { 
	    	 throw new RuntimeException("Not found."); // custom exception here!
	     }
	     
	     seatToUpdate.setSeatColumn(seat.getSeatColumn());
	     seatToUpdate.setSeatRow(seat.getSeatRow());
	     seatToUpdate.setSector(seat.getSector());
	     
	     Seat updatedSeat = seatRepository.save(seatToUpdate);
	        
	     return updatedSeat;
	}

	@Override
	public void delete(Long id) {
		seatRepository.deleteById(id);
	}

}

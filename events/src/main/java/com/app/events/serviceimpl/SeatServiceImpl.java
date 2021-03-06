package com.app.events.serviceimpl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Seat;
import com.app.events.repository.SeatRepository;
import com.app.events.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService {

	@Autowired
	private SeatRepository seatRepository;


	@Override
	public Seat findOne(Long id) throws ResourceNotFoundException{
		return this.seatRepository.findById(id)
                    .orElseThrow(
                        ()-> new ResourceNotFoundException("Seat")
                    ); 
	}

	@Override
	public Collection<Seat> findSeatFromSector(Long id){
		return seatRepository.findSeatBySectorId(id);
	}
	
	@Override
	public Seat create(Seat seat) throws Exception{
		if(seat.getId() != null)
		{
			throw new ResourceExistsException("Seat");
		}
		
		return seatRepository.save(seat);
	}

	@Override
	public Seat update(Seat seat) throws Exception {
		Seat seatToUpdate = this.findOne(seat.getId());
		prepareSeatFields(seatToUpdate, seat);
		if(checkSeatFieldsAvailability(seatToUpdate))
		{
			return seatRepository.save(seatToUpdate);
		}
		else{
			throw new ResourceExistsException("Seat");
		}
	}

	@Override
	public void delete(Long id) {
		seatRepository.deleteById(id);
	}

	public Seat prepareSeatFields(Seat toUpdate, Seat newSeat){
		toUpdate.setSeatColumn(newSeat.getSeatColumn());
		toUpdate.setSeatRow(newSeat.getSeatRow());
		toUpdate.setSector(newSeat.getSector());
		return toUpdate;
	}

	public boolean checkSeatFieldsAvailability(Seat seat){
		Optional<Seat> optSeat = seatRepository.findSeatByAllParams
							(seat.getSeatColumn(), seat.getSeatRow(), seat.getSector().getId());
		if(optSeat.isPresent() && (seat.getId() != optSeat.get().getId())){
			return false;
		}
		return true;
	}
}

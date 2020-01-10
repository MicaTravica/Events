package com.app.events.serviceimpl.seat;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.SeatConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.repository.SeatRepository;
import com.app.events.serviceimpl.SeatServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SeatServiceImplIntegrationTest {

	@Autowired
	private SeatServiceImpl seatService;
	
	@Autowired
	private SeatRepository seatRepository;
	
	
	
	@Test(expected = ResourceNotFoundException.class)
	public void findOne_TestFail() throws ResourceNotFoundException {
		seatService.findOne(SeatConstants.INVALID_SEAT_ID);
		
	}
	

	@Test
	public void findOne_TestSuccess() throws ResourceNotFoundException  {
		Seat seats = seatService.findOne(SeatConstants.PERSISTED_SEAT_ID);
		
		assertEquals(seats.getId(), SeatConstants.PERSISTED_SEAT_ID);
		assertEquals(seats.getSeatColumn(), SeatConstants.PERSISTED_SEAT_COLUMN);
		assertEquals(seats.getSeatRow(), SeatConstants.PERSISTED_SEAT_ROW);
		
	}
	
	@Test
	public void findSeatFromSector_TestFail() throws Exception{
		
		Collection<Seat> seats = seatService.findSeatFromSector(SectorConstants.INVALID_SECTOR_ID);
	
		assertEquals(0, seats.size());
		
	}
	
	@Test
	public void findSeatFromSector_TestSuccess() throws Exception{
		
		Collection<Seat> seats = seatService.findSeatFromSector(SectorConstants.PERSISTED_SECTOR_ID);
	
		assertEquals(1, seats.size());
		
	}
	
	@Test
	@Rollback
	@Transactional
	public void create_TestSuccess() throws Exception {
		int numberOfSeats = seatRepository.findAll().size();
		
		Seat seat = new Seat(
				null,
				SeatConstants.PERSISTED_SEAT_ROW,
				SeatConstants.PERSISTED_SEAT_COLUMN,
				new Sector(SectorConstants.PERSISTED_SECTOR_ID)
				);
		
		Seat savedSeat = seatService.create(seat);
		
		assertEquals(numberOfSeats + 1, seatRepository.findAll().size());
		assertEquals(savedSeat.getSeatColumn(), SeatConstants.PERSISTED_SEAT_COLUMN);
		assertEquals(savedSeat.getSeatRow(), SeatConstants.PERSISTED_SEAT_ROW);
		
	}
	
	@Test(expected = ResourceExistsException.class)
	@Rollback
	@Transactional
	public void createSeat_TestFail() throws Exception {
		seatService.create(new Seat(SeatConstants.INVALID_SEAT_ID));
	}
	
	@Test(expected = ResourceNotFoundException.class)
	@Rollback
	@Transactional
    public void updateSeat_when_SeatDoesntExist() throws Exception{
		seatService.update(new Seat(SeatConstants.INVALID_SEAT_ID));
	}
	
	@Test
	@Rollback
	@Transactional
	public void update_TestSuccess() throws Exception {
		Seat seats = seatService.findOne(SeatConstants.PERSISTED_SEAT_ID);
		seats.setSeatColumn(2);
		seats.setSeatRow(2);
		
		Seat seat = seatService.update(seats);
		
		assertEquals(seat.getId(), SeatConstants.PERSISTED_SEAT_ID);
		assertEquals(seat.getSeatColumn(), 2);
		assertEquals(seat.getSeatRow(), 2);
	}
	
	
}

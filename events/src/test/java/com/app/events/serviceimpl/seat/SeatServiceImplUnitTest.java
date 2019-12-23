package com.app.events.serviceimpl.seat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.SeatConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Seat;
import com.app.events.model.Sector;
import com.app.events.repository.SeatRepository;
import com.app.events.service.SectorService;
import com.app.events.serviceimpl.SeatServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SeatServiceImplUnitTest {

	public static Seat NEW_SEAT = null;
	public static Seat INVALID_SEAT = null;
	public static Seat SAVED_SEAT = null;
	public static Seat SAVED_SEAT_2 = null;
	
	@Autowired
	private SeatServiceImpl seatService;
	
	@MockBean 
	private SeatRepository seatRepository;
	
	@MockBean 
	private SectorService sectorService;
	

	
	@Before
	public void setUp() throws Exception{
		NEW_SEAT = new Seat(
				null,
				SeatConstants.PERSISTED_SEAT_ROW,
				SeatConstants.PERSISTED_SEAT_COLUMN
				);
		
		INVALID_SEAT = new Seat(SeatConstants.INVALID_SEAT_ID);
		
		SAVED_SEAT = new Seat(
				SeatConstants.PERSISTED_SEAT_ID,
				SeatConstants.PERSISTED_SEAT_ROW,
				SeatConstants.PERSISTED_SEAT_COLUMN
				);
		
		SAVED_SEAT_2 = new Seat(
				SeatConstants.SEAT_ID_2,
				SeatConstants.PERSISTED_SEAT_ROW,
				SeatConstants.PERSISTED_SEAT_COLUMN
				
				);
		
		when(seatRepository.findById(SeatConstants.PERSISTED_SEAT_ID)).thenReturn(Optional.of(SAVED_SEAT));
		when(seatRepository.findById(SeatConstants.INVALID_SEAT_ID)).thenReturn(Optional.empty());
	
		
		when(seatRepository.save(NEW_SEAT)).thenReturn(NEW_SEAT);
		when(seatRepository.save(SAVED_SEAT)).thenReturn(SAVED_SEAT);
		
	    
		
		
	}
	
	@Test
	public void findSeat_TestSuccess() throws Exception{
		Seat seat = seatService.findOne(SeatConstants.PERSISTED_SEAT_ID);
		assertNotNull(seat);
		assertEquals(seat.getId(), SeatConstants.PERSISTED_SEAT_ID);
		assertEquals(seat.getSeatColumn(), SeatConstants.PERSISTED_SEAT_COLUMN);
		assertEquals(seat.getSeatRow(), SeatConstants.PERSISTED_SEAT_ROW);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void findSeat_TestFail() throws Exception{
	
		seatService.findOne(SeatConstants.INVALID_SEAT_ID);
		
	}
	
	@Test
	public void findSeatFromSector_TestFail() throws Exception{
		
		Collection<Seat> seats = seatService.findSeatFromSector(SectorConstants.INVALID_SECTOR_ID);
	
		assertEquals(0, seats.size());
		
	}
	
	
	@Test
	public void createSeat_TestSuccess() throws Exception{
		
		Sector sector = new Sector(SectorConstants.PERSISTED_SECTOR_ID);
		
		NEW_SEAT.setSector(sector);
		Seat seat = seatService.create(NEW_SEAT);
		
		assertEquals(NEW_SEAT.getId(), seat.getId());
	
	}
	
	@Test(expected = ResourceExistsException.class)
	public void createSeat_TestFail() throws Exception {
		seatService.create(SAVED_SEAT);
	}
	

	@Test(expected = ResourceNotFoundException.class)
    public void updateSeat_when_SeatDoesntExist() throws Exception{
		seatService.update(new Seat(SeatConstants.INVALID_SEAT_ID));
	}
	
	@Test
	public void updateSeat_TestSuccess() throws Exception {
		SAVED_SEAT.setSector(new Sector(SectorConstants.PERSISTED_SECTOR_ID));
		Seat seat = seatService.update(SAVED_SEAT);
		
		
		assertEquals(seat.getSector().getId(), SectorConstants.PERSISTED_SECTOR_ID);
		assertEquals(seat.getId(), SeatConstants.PERSISTED_SEAT_ID);
		assertEquals(seat.getSeatColumn(), SeatConstants.PERSISTED_SEAT_COLUMN);
		assertEquals(seat.getSeatRow(), SeatConstants.PERSISTED_SEAT_ROW);
		
	}
	
	
	
}

package com.app.events.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.SeatConstants;
import com.app.events.model.Seat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class SeatRepositoryTest {
	
	@Autowired
	private SeatRepository seatRepository;

	@Test
	public void findSeatByAllParams_TestSuccess() {
		int column = 1;
		int row = 1;
		Long sectorID = 1l;
		
		Optional<Seat> results = seatRepository.findSeatByAllParams(column, row, sectorID);
		
		Seat seat = results.get();
		assertEquals(seat.getId(), SeatConstants.PERSISTED_SEAT_ID);
		
	}
	
	@Test
	public void findSeatByAllParams_TestFail_Column() {
		int column = 3;
		int row = 1;
		Long sectorID = 1l;
		
		Optional<Seat> results = seatRepository.findSeatByAllParams(column, row, sectorID);
				
		assertSame(Optional.empty(), results);
		
	}
	
	@Test
	public void findSeatByAllParams_TestFail() {
		int column = 1;
		int row = 10;
		Long sectorID = 12l;
		
		Optional<Seat> results = seatRepository.findSeatByAllParams(column, row, sectorID);
				
		assertSame(Optional.empty(), results);
		
	}
	
	@Test
	public void findSeatBySectorId_TestSuccess() {
		
		Long sectorID = 1l;
		
		Collection<Seat> results = seatRepository.findSeatBySectorId(sectorID);
		
		int numberOfSeat = results.size();
		
		assertEquals(numberOfSeat, 1);
		
		Seat first = results.iterator().next();
		
		assertEquals(first.getId(), SeatConstants.PERSISTED_SEAT_ID);
	
		
	}
	
	@Test
	public void findSeatBySectorId_TestFail() {
		
		Long sectorID = 10l;
		
		Collection<Seat> results = seatRepository.findSeatBySectorId(sectorID);
		
		int numberOfSeat = results.size();
		
		assertEquals(numberOfSeat, 0);
	
	
		
	}
	
	
}

package com.app.events.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


import java.util.Collection;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.TicketConstants;
import com.app.events.constants.UserConstants;
import com.app.events.model.Ticket;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class TicketRepositoryTest {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Test
	public void ticketForEventIsSale_Test_Fail() {
		
		boolean vrednost = ticketRepository.ticketForEventIsSale(1l);
		assertFalse(vrednost);
	}
	
	@Test
	public void ticketForEventIsSale_Test_Success() {
		
		boolean vrednost = ticketRepository.ticketForEventIsSale(2l);
		assertTrue(vrednost);
	}
	
	@Test
	public void findAllByEventId_Test_Success() {
		
		Collection<Ticket> results = ticketRepository.findAllByEventId(1l);
		assertFalse(results.isEmpty());
		
		for(Ticket t : results) {
			assertEquals(TicketConstants.PERSISTED_TICKET_EVENT_ID, t.getEvent().getId());
		}
	}
	
	@Test
	public void findAllByEventId_Test_Fail() {
		
		Collection<Ticket> results = ticketRepository.findAllByEventId(3l);
		assertTrue(results.isEmpty());
	}
	
	
	@Test
	public void reservesionByUserId_Test_Fail() {
		
		Collection<Ticket> results = ticketRepository.findAllReservationsByUserId(UserConstants.INVALED_USERNAME);
		assertTrue(results.isEmpty());
	}
	
	@Test
	public void reservesionByUserId_Test_Success() {
		
		Collection<Ticket> results = ticketRepository.findAllReservationsByUserId(UserConstants.DB_USER_USERNAME);
		assertFalse(results.isEmpty());
	}
	
	@Test
	public void findAllTicketByUserId_Test_Fail() {
		Pageable pageable = PageRequest.of(0, 10);

		Page<Ticket> results = ticketRepository.findAllTicketsByUserId(UserConstants.INVALED_USERNAME, pageable);
		assertTrue(results.isEmpty());
	}
	@Test
	public void findAllTicketByUserId_Test_Success() {
		Pageable pageable = PageRequest.of(0, 10);

		Page<Ticket> results = ticketRepository.findAllTicketsByUserId(UserConstants.DB_USER_USERNAME, pageable);
		assertFalse(results.isEmpty());
	}
	
	@Test
	public void findAllReservationsByUserId_Test_Fail() {
		
		Collection<Ticket> results = ticketRepository.findAllReservationsByUserId("milica");
		assertTrue(results.isEmpty());
	}
	
	@Test
	public void findProfitByEventId_Test_Success() {
		
		Double profit = ticketRepository.findProfitByEventId(2l);
		assertFalse(profit.isNaN());		
	}
	
	@Test
	public void findProfitByEventId_Test_Fail() {
		
		Double profit = ticketRepository.findProfitByEventId(4l);
		assertEquals(null, profit);
	}
	
	@Test
	public void findAttendanceByEventId_Test_Success() {
		
		Double number = ticketRepository.findAttendanceByEventId(2l);
		assertFalse(number.isNaN());
	}
	
	
	
	@Test
	public void availableTikcketsByEventId_Test_Success() {
		int number = ticketRepository.availableTikcketsByEventId(1l);
		assertEquals(1, number);
		
	}
	
	@Test
	public void availableTikcketsByEventId_Test_Fail() {
		int number = ticketRepository.availableTikcketsByEventId(2l);
		assertEquals(2, number);
		
	}
	
	

	
	
	@Test 
	public void findTicketsBySeatId_Test_Success() {
		Collection<Ticket> tickets = ticketRepository.findTicketsBySeatId(1l);
		
		assertFalse(tickets.isEmpty());
		for(Ticket t : tickets) {
			assertEquals(TicketConstants.PERSISTED_TICKET_SEAT_ID, t.getSeat().getId());
		}
		
	}
	
	@Test 
	public void findTicketsBySeatId_Test_Fail() {
		Collection<Ticket> tickets = ticketRepository.findTicketsBySeatId(2l);
		
		assertTrue(tickets.isEmpty());
		
		
	}
	
	
	@Test
	public void findTicketsBySectorCapacityId_Test_Fail() {
		Collection<Ticket> tickets = ticketRepository.findTicketsBySectorCapacityId(1l);
		
		assertTrue(tickets.isEmpty());
	}
	
	@Test
	public void findTicketsBySectorCapacityId_Test_Success() {
		Collection<Ticket> tickets = ticketRepository.findTicketsBySectorCapacityId(4l);
		
		assertTrue(tickets.isEmpty());
	}
	
	
	
	
}

package com.app.events.serviceimpl.ticket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.SectorCapacityConstants;
import com.app.events.constants.TicketConstants;
import com.app.events.constants.UserConstants;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.SectorCapacity;
import com.app.events.model.Ticket;
import com.app.events.model.User;
import com.app.events.repository.SectorCapacityRepository;
import com.app.events.repository.TicketRepository;
import com.app.events.serviceimpl.SectorCapacityServiceImpl;
import com.app.events.serviceimpl.TicketServiceImpl;
import com.app.events.serviceimpl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class TicketServiceImplIntegrationTest {

	 @Autowired
	 private TicketServiceImpl ticketServiceImpl;
	 
	 @Autowired
	 private UserServiceImpl userServiceImpl;

	 @Autowired
	 private TicketRepository ticketRepository;
	 
	@Test
	public void findOneById_Test_Success() throws ResourceNotFoundException
	{
		 Ticket ticket = ticketServiceImpl.findOne(TicketConstants.PERSISTED_TICKET_ID);

		 assertEquals(TicketConstants.PERSISTED_TICKET_BAR_CODE, ticket.getBarCode());
		 assertEquals(TicketConstants.PERSISTED_TICKET_EVENT_ID, ticket.getEvent().getId());
		 assertEquals(TicketConstants.PERSISTED_TICKET_SEAT_ID, ticket.getSeat().getId());
		 
	}

	@Test(expected = ResourceNotFoundException.class)
	public void findOneById_Test_Fail() throws ResourceNotFoundException
	{
		 ticketServiceImpl.findOne(TicketConstants.INVALID_TICKET_ID);
	}
	  
	@Test
	public void findAllByEventId_Test_Success() throws ResourceNotFoundException 
	{
		Collection<Ticket> results = ticketServiceImpl.findAllByEventId(1l);
		assertFalse(results.isEmpty());
			
		for(Ticket t : results) {
			assertEquals(TicketConstants.PERSISTED_TICKET_EVENT_ID, t.getEvent().getId());
		}
	}
		
	@Test(expected = ResourceNotFoundException.class)
	@Transactional
	@Rollback(true)
	 public void findAllByEventId_Test_Fail() throws ResourceNotFoundException {
			
		 Collection<Ticket> results = ticketServiceImpl.findAllByEventId(3l);
		 assertTrue(results.isEmpty());
	 }
	  
	@Test
	 public void ticketForEventIsSale_Test_Fail() {
			
		 boolean vrednost = ticketServiceImpl.ticketForEventIsSale(TicketConstants.PERSISTED_TICKET_ID);
		 assertFalse(vrednost);
	 }
		

	@Test
	public void findProfitByEventId_Test_Success() {
		
		Double profit = ticketServiceImpl.findProfitByEventId(2l);
		assertFalse(profit.isNaN());		
	}
	
	@Test
	public void findProfitByEventId_Test_Fail() {
		
		Double profit = ticketServiceImpl.findProfitByEventId(TicketConstants.PERSISTED_TICKET_EVENT_ID);
		assertEquals(null, profit);
	}
	
	@Test
	public void findAttendanceByEventId_Test_Success() {
		
		Double number = ticketServiceImpl.findAttendanceByEventId(2l);
		assertFalse(number.isNaN());
	}

	@Test
	public void findAllTicketByUserId_Test_Fail() {
		
		Page<Ticket> profit = ticketServiceImpl.findAllTicketsByUserId(UserConstants.DB_ADMIN_USERNAME, 1, 1);
		assertTrue(profit.isEmpty());		
	}
	

	@Test
	public void findAllReservationsByUserId_Test_Fail() {
		
		Collection<Ticket> profit = ticketServiceImpl.findAllReservationsByUserId(UserConstants.DB_ADMIN_USERNAME);
		assertTrue(profit.isEmpty());		
	}


	
	
}

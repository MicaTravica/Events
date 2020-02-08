package com.app.events.serviceimpl.priceList;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.EventConstants;
import com.app.events.constants.PriceListConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.PriceList;
import com.app.events.model.Sector;
import com.app.events.repository.PriceListRepository;
import com.app.events.serviceimpl.PriceListServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PriceListImplIntegrationTest {

	@Autowired
	private PriceListServiceImpl priceListService;
	
	@Autowired
	private PriceListRepository priceListRepository;
	
	@Test
	public void findOne_TestSuccess() throws ResourceNotFoundException {
		PriceList priceList = priceListService.findOne(PriceListConstants.PERSISTED_PL_ID);
		assertEquals(PriceListConstants.PERSISTED_EVENT_ID, priceList.getEvent().getId());
		assertEquals(PriceListConstants.PERSISTED_SECTOR_ID, priceList.getSector().getId());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void findOne_TestFail() throws ResourceNotFoundException {
		priceListService.findOne(PriceListConstants.INVALID_PL_ID);
	}
	
	@Test
	@Rollback
	@Transactional
	public void createPlace_TestSuccess() throws Exception {
		
		int numberOfPlaces = priceListRepository.findAll().size();
		
		PriceList pl = new PriceList(
				null,
				PriceListConstants.PERSISTED_PRICE,
				new Event(PriceListConstants.NEW_EVENT_ID),
				new Sector(PriceListConstants.NEW_SECTOR_ID)
				);
		
		PriceList savedPL = priceListService.create(pl);
		
		assertEquals(numberOfPlaces + 1, priceListRepository.findAll().size());
		assertEquals(PriceListConstants.NEW_EVENT_ID, savedPL.getEvent().getId());
		assertEquals(PriceListConstants.NEW_SECTOR_ID, savedPL.getSector().getId());
		
		
		priceListService.delete(savedPL.getId());
		
		
	}
	
	@Test(expected = ResourceExistsException.class)
	@Rollback
	@Transactional
	public void createPlace_TestFail() throws Exception {
				
		PriceList pl = new PriceList(
				PriceListConstants.PERSISTED_PL_ID,
				PriceListConstants.PERSISTED_PRICE,
				new Event(PriceListConstants.NEW_EVENT_ID),
				new Sector(PriceListConstants.NEW_SECTOR_ID)
				);
		
		priceListService.create(pl);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	@Rollback
	@Transactional
	public void createPlace_Test_Event_Fail() throws Exception {
				
		PriceList pl = new PriceList(
				null,
				PriceListConstants.PERSISTED_PRICE,
				new Event(EventConstants.INVALID_EVENT_ID),
				new Sector(PriceListConstants.NEW_SECTOR_ID)
				);
		
		priceListService.create(pl);
	}
	

	@Test(expected = ResourceNotFoundException.class)
	@Rollback
	@Transactional
	public void createPlace_Test_Sector_Fail() throws Exception {
				
		PriceList pl = new PriceList(
				null,
				PriceListConstants.PERSISTED_PRICE,
				new Event(PriceListConstants.PERSISTED_EVENT_ID),
				new Sector(SectorConstants.INVALID_SECTOR_ID)
				);
		
		priceListService.create(pl);
	}
	
	@Test
	public void updatePriceList_TestSuccess() throws Exception
	{
		int numberOfPlaces = priceListRepository.findAll().size();

		PriceList pl = new PriceList(
				PriceListConstants.PERSISTED_PL_ID,
				PriceListConstants.NEW_PRICE,
				new Event(PriceListConstants.PERSISTED_EVENT_ID),
				new Sector(PriceListConstants.PERSISTED_SECTOR_ID)
				);
		
		priceListService.update(pl);
		 
		assertEquals(numberOfPlaces, priceListRepository.findAll().size());
	}
	

	@Test(expected = ResourceNotFoundException.class)
	@Rollback
	@Transactional
	public void updatePriceList_TestFail() throws Exception
	{

		PriceList pl = new PriceList(PriceListConstants.INVALID_PL_ID);
		priceListService.update(pl);

	}
	
	
	
}
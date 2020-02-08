package com.app.events.serviceimpl.priceList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.PlaceConstants;
import com.app.events.constants.PriceListConstants;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.Place;
import com.app.events.model.PriceList;
import com.app.events.model.Sector;
import com.app.events.repository.PriceListRepository;
import com.app.events.serviceimpl.PriceListServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PriceListImplUnitTest {
	
	public static PriceList NEW_PL = null;
	public static PriceList INVALID_PL= null;
	public static PriceList SAVED_PL = null;
	
	@Autowired
	private PriceListServiceImpl plService;
	
	@MockBean
	private PriceListRepository plRepository;

	@Before
	public void setUp() throws Exception{
		
		NEW_PL = new PriceList(
				null,
				PriceListConstants.PERSISTED_PRICE,
				new Event(PriceListConstants.PERSISTED_EVENT_ID),
				new Sector(PriceListConstants.PERSISTED_SECTOR_ID)
				);
		
		INVALID_PL = new PriceList(PriceListConstants.INVALID_PL_ID);
		
		SAVED_PL = new PriceList(
				PriceListConstants.PERSISTED_PL_ID,
				PriceListConstants.PERSISTED_PRICE,
				new Event(PriceListConstants.PERSISTED_EVENT_ID),
				new Sector(PriceListConstants.PERSISTED_SECTOR_ID)
				);
		
		
		when(plRepository.findById(PriceListConstants.PERSISTED_PL_ID)).thenReturn(Optional.of(SAVED_PL));
		when(plRepository.findById(PriceListConstants.INVALID_PL_ID)).thenReturn(Optional.empty());
		
		when(plRepository.save(NEW_PL)).thenReturn(NEW_PL);
		when(plRepository.save(SAVED_PL)).thenReturn(SAVED_PL);
		
	
		
	}
	
	@Test
	public void findOne_TestSuccess() throws Exception {
		PriceList pl = plService.findOne(SAVED_PL.getId());
		assertNotNull(pl);
		assertEquals(SAVED_PL.getSector(), pl.getSector());
		assertEquals(SAVED_PL.getEvent(), pl.getEvent());
		
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void findOne_TestFail_ThenThrows_ResourceNotFoundException() throws ResourceNotFoundException{
		plService.findOne(PriceListConstants.INVALID_PL_ID);
	}
	
    
    @Test(expected = ResourceExistsException.class)
   	public void create_TestFail_ResourceExistsException() throws Exception{

       	plService.create(SAVED_PL);
   	}
	

    @Test(expected = ResourceNotFoundException.class)
    public void update_TestFail() throws Exception{
    	plService.update(new PriceList(PriceListConstants.INVALID_PL_ID));
    }
    
    @Test
    public void update_TestSuccess() throws Exception {
    	SAVED_PL.setPrice(1200);
    	plService.update(SAVED_PL);
    	
    	
    }
    
}

package com.app.events.serviceimpl.place;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.HallConstants;
import com.app.events.constants.PlaceConstants;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.EventType;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.model.SearchParamsEvent;
import com.app.events.model.SearchParamsPlace;
import com.app.events.repository.PlaceRepository;
import com.app.events.serviceimpl.PlaceServiceImpl;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlaceServiceImplUnitTest {

	public static Place NEW_PLACE = null;
	public static Place INVALID_PLACE = null;
	public static Place SAVED_PLACE = null;
	public static Place PLACE_2 = null;
	public static Place PLACE_3 = null;



	 
	@Autowired
	private PlaceServiceImpl placeService;
	
	@MockBean
	private PlaceRepository placeRepository;

	@Before
	public void setUp() throws Exception{
		
		NEW_PLACE = new Place(
				null,
				PlaceConstants.PERSISTED_PLACE_NAME,
				PlaceConstants.PERSISTED_PLACE_ADDRESS,
				PlaceConstants.PERSISTED_PLACE_LATITUDE,
				PlaceConstants.PERSISTED_PLACE_LONGITUDE
				);
		
		INVALID_PLACE = new Place(PlaceConstants.INVALID_PLACE_ID);
		
		SAVED_PLACE = new Place(
				PlaceConstants.PERSISTED_PLACE_ID,
				PlaceConstants.PERSISTED_PLACE_NAME,
				PlaceConstants.PERSISTED_PLACE_ADDRESS,
				PlaceConstants.PERSISTED_PLACE_LATITUDE,
				PlaceConstants.PERSISTED_PLACE_LONGITUDE
				);
		
		PLACE_2 = new Place(
				PlaceConstants.PLACE_ID_FAIL,
				PlaceConstants.NEW_PLACE_NAME,
				PlaceConstants.PERSISTED_PLACE_ADDRESS,
				PlaceConstants.NEW_PLACE_LATITUDE,
				PlaceConstants.NEW_PLACE_LONGITUDE
				); 
	
		
		when(placeRepository.findById(PlaceConstants.PERSISTED_PLACE_ID)).thenReturn(Optional.of(SAVED_PLACE));
		when(placeRepository.findById(PlaceConstants.INVALID_PLACE_ID)).thenReturn(Optional.empty());
		
		when(placeRepository.save(NEW_PLACE)).thenReturn(NEW_PLACE);
		when(placeRepository.save(SAVED_PLACE)).thenReturn(SAVED_PLACE);
		
	
		
	}

	
	@Test
	public void findOne_TestSuccess() throws Exception {
		Place place = placeService.findOne(SAVED_PLACE.getId());
		assertNotNull(place);
		assertEquals(SAVED_PLACE.getId(), place.getId());
		assertEquals(SAVED_PLACE.getName(), place.getName());
		assertEquals(SAVED_PLACE.getAddress(), place.getAddress());
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void findOne_TestFail_ThenThrows_ResourceNotFoundException() throws ResourceNotFoundException{
		placeService.findOne(PlaceConstants.INVALID_PLACE_ID);
	}
	

	@Test(expected = ResourceNotFoundException.class)
	public void findOneAndLoadHalls_TestFail() throws ResourceNotFoundException {
		placeService.findOneAndLoadHalls(PlaceConstants.INVALID_PLACE_ID);
	}
	
	@Test
	public void create_TestSuccess() throws Exception{
		
		Place place = placeService.create(NEW_PLACE);
		
		assertEquals(NEW_PLACE.getId(), place.getId());
		assertEquals(NEW_PLACE.getName(), place.getName());	
		assertEquals(NEW_PLACE.getAddress(), place.getAddress());
		
	}
	
    @Test
	public void create_TestFail_ResourceExistsException() throws Exception{

		placeService.create(PLACE_2);
	}
    
    @Test(expected = ResourceNotFoundException.class)
    public void update_TestFail() throws Exception{
    	placeService.update(new Place(PlaceConstants.INVALID_PLACE_ID));
    }
    
    @Test
    public void update_TestSuccess() throws Exception {
    	SAVED_PLACE.setAddress("Nova Adresa");
    	Place place = placeService.update(SAVED_PLACE);
    	
    	assertEquals(place.getAddress(), "Nova Adresa");
    	
    }
    


    
   

}

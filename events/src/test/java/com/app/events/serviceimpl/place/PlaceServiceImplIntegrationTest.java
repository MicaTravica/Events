package com.app.events.serviceimpl.place;

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

import com.app.events.constants.PlaceConstants;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Place;
import com.app.events.repository.PlaceRepository;
import com.app.events.serviceimpl.PlaceServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PlaceServiceImplIntegrationTest {

	@Autowired
	private PlaceServiceImpl placeService;
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Test
	public void findOne_TestSuccess() throws ResourceNotFoundException {
		Place place = placeService.findOne(PlaceConstants.PERSISTED_PLACE_ID);
		assertEquals(PlaceConstants.PERSISTED_PLACE_ID, place.getId());
		assertEquals(PlaceConstants.PERSISTED_PLACE_NAME, place.getName());
		assertEquals(PlaceConstants.PERSISTED_PLACE_ADDRESS, place.getAddress());
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void findOne_TestFail() throws ResourceNotFoundException {
		placeService.findOne(PlaceConstants.INVALID_PLACE_ID);
	}
	
	
	@Test
	@Rollback
	@Transactional
	public void createPlace_TestSuccess() throws Exception {
		
		int numberOfPlaces = placeRepository.findAll().size();
		
		Place place = new Place(
				null,
				PlaceConstants.NEW_PLACE_NAME,
				PlaceConstants.NEW_PLACE_ADDRESS,
				PlaceConstants.NEW_PLACE_LATITUDE, 
				PlaceConstants.NEW_PLACE_LONGITUDE
				);
		
		Place savedPlace = placeService.create(place);
		
		assertEquals(numberOfPlaces + 1, placeRepository.findAll().size());
		assertEquals(PlaceConstants.NEW_PLACE_NAME, savedPlace.getName());
		assertEquals(PlaceConstants.NEW_PLACE_ADDRESS, savedPlace.getAddress());
		
		placeRepository.delete(savedPlace);
		
		
	}

	@Test(expected = ResourceExistsException.class)
	@Rollback
	@Transactional
	public void createPlace_TestFail_CoordinateExist() throws Exception {
		
		Place place = new Place(
				null,
				PlaceConstants.NEW_PLACE_ADDRESS,
				PlaceConstants.NEW_PLACE_ADDRESS,
				PlaceConstants.PERSISTED_PLACE_LATITUDE, 
				PlaceConstants.PERSISTED_PLACE_LONGITUDE
				);
		
		placeService.create(place);
		
	}
	
	@Test(expected = ResourceExistsException.class)
	@Rollback
	@Transactional
	public void createPlace_TestFail_AddressExist() throws Exception {
		
		Place place = new Place(
				null,
				PlaceConstants.NEW_PLACE_ADDRESS,
				PlaceConstants.PERSISTED_PLACE_ADDRESS,
				PlaceConstants.PERSISTED_PLACE_LATITUDE, 
				PlaceConstants.PERSISTED_PLACE_LONGITUDE
				);
		
		placeService.create(place);
		
	}
	
	
	@Test(expected = ResourceExistsException.class)
	@Rollback
	@Transactional
	public void createPlace_TestFail() throws Exception {

		Place place = new Place(
				PlaceConstants.PERSISTED_PLACE_ID,
				PlaceConstants.NEW_PLACE_NAME,
				PlaceConstants.NEW_PLACE_ADDRESS,
				PlaceConstants.PERSISTED_PLACE_LATITUDE, 
				PlaceConstants.PERSISTED_PLACE_LONGITUDE
				);
		
		placeService.create(place);
	}
	
	
	@Test
	@Rollback
	@Transactional
	public void updatePlace_TestSuccess() throws Exception
	{
		int numberOfPlaces = placeRepository.findAll().size();

		Place place = new Place(
			PlaceConstants.PERSISTED_PLACE_ID,
			PlaceConstants.PERSISTED_PLACE_NAME,
			PlaceConstants.PERSISTED_PLACE_ADDRESS,
			PlaceConstants.PERSISTED_PLACE_LATITUDE,
			PlaceConstants.PERSISTED_PLACE_LONGITUDE
				 
		 );
		
		Place savedPlace = placeService.update(place);
		 
		assertEquals(numberOfPlaces, placeRepository.findAll().size());
		assertEquals(savedPlace.getAddress(), PlaceConstants.PERSISTED_PLACE_ADDRESS);
		assertEquals(savedPlace.getName(), PlaceConstants.PERSISTED_PLACE_NAME);
		

	}
	
	@Test(expected = ResourceNotFoundException.class)
	@Rollback
	@Transactional
	public void updatePlace_TestFail() throws Exception
	{

		Place place = new Place(PlaceConstants.INVALID_PLACE_ID);
		placeService.update(place);

	}

}

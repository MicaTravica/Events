package com.app.events.serviceimpl.hall;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collection;
import java.util.HashSet;

import com.app.events.constants.HallConstants;
import com.app.events.constants.PlaceConstants;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.repository.HallRepository;
import com.app.events.serviceimpl.HallServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * HallServiceImplIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class HallServiceImplIntegrationTest {

    @Autowired
    public HallServiceImpl hallService;
    
    @Autowired
    public HallRepository hallRepository;

    @Test
    public void findOne_whenHallExsists() throws Exception
    {
        Hall hall = hallService.findOne(HallConstants.PERSISTED_HALL_ID);
        assertNotNull(hall);
        assertEquals(HallConstants.PERSISTED_HALL_ID, hall.getId());
        assertEquals(HallConstants.PERSISTED_HALL_NAME, hall.getName());
        assertEquals(HallConstants.PERSISTED_HALL_PLACE_ID, hall.getPlace().getId());
    }
    
    @Test(expected = ResourceNotFoundException.class )
    public void findOne_whenHallDoesNotExsist() throws Exception
    {
        hallService.findOne(HallConstants.INVALID_HALL_ID);
    }
    
    @Test
    public void findOneAndLoadSectors_whenHallExsists() throws Exception
    {
        Hall hall = hallService.findOneAndLoadSectors(HallConstants.PERSISTED_HALL_ID);
        assertNotNull(hall);
        assertEquals(HallConstants.PERSISTED_HALL_ID, hall.getId());
        assertEquals(HallConstants.PERSISTED_HALL_NAME, hall.getName());
        assertEquals(HallConstants.PERSISTED_HALL_PLACE_ID, hall.getPlace().getId());
    }
    
    @Test(expected = ResourceNotFoundException.class )
    public void findOneAndLoadSectors_whenHallDoesNotExsist() throws Exception
    {
        hallService.findOneAndLoadSectors(HallConstants.INVALID_HALL_ID);
    }
    

    @Test(expected = ResourceExistsException.class)
    public void saveHall_when_Hall_ID_AllreadyGiven() throws Exception
    {
        Hall h = new Hall(HallConstants.PERSISTED_HALL_ID,
                            HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE,
                            new Place(PlaceConstants.PERSISTED_PLACE_ID),
                            new HashSet<>(), new HashSet<>());
        hallService.create(h);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveHall_whenHallPlaceNotExists() throws Exception
    {
        Hall h = new Hall(  null, 
                            HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE,
                            new Place(PlaceConstants.INVALID_PLACE_ID),
                            new HashSet<>(), new HashSet<>());
        hallService.create(h);
    }

    @Test
    public void saveHall_whenAllDataValid() throws Exception
    {
        int numberOfHallBefore = hallRepository.findAll().size();

        Hall h = new Hall(  null, 
                            HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE,
                            new Place(PlaceConstants.PERSISTED_PLACE_ID),
                            new HashSet<>(), new HashSet<>());
        Hall savedHall = hallService.create(h);

        assertEquals(HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE, savedHall.getName());
        assertEquals(PlaceConstants.PERSISTED_PLACE_ID, savedHall.getPlace().getId());
        // chech if is acualy added to db and then remove it...
        assertEquals(numberOfHallBefore + 1, hallRepository.findAll().size() );
        
        hallRepository.delete(savedHall);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateHall_when_Hall_DoesNotExist() throws Exception
    {
        Hall h = new Hall(  HallConstants.INVALID_HALL_ID, 
                            HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE,
                            new Place(PlaceConstants.PERSISTED_PLACE_ID),
                            new HashSet<>(), new HashSet<>());
        hallService.update(h);
    }

    @Test
    public void updateHall_when_allGood() throws Exception
    {
        int numberOfHallBefore = hallRepository.findAll().size();

        Hall h = new Hall(  HallConstants.PERSISTED_HALL_ID, 
                            HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE,
                            new Place(PlaceConstants.PERSISTED_PLACE_ID),
                            new HashSet<>(), new HashSet<>());
        Hall updatedHall = hallService.update(h);

        assertEquals(numberOfHallBefore, hallRepository.findAll().size());
        assertEquals(HallConstants.PERSISTED_HALL_ID, updatedHall.getId());
        assertEquals(HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE, updatedHall.getName());

        // set old hall name so other test can use old name in checks
        updatedHall.setName(HallConstants.PERSISTED_HALL_NAME);
        hallRepository.save(updatedHall);
    }


    @Test
    public void getHallsByPlaceId_Test_Success() {
    	Collection<Hall> results = hallRepository.findAllByPlaceId(HallConstants.PERSISTED_HALL_PLACE_ID);
    	Hall hall = results.iterator().next();
		
		assertEquals(results.size(),3);
		assertEquals(hall.getId(), HallConstants.PERSISTED_HALL_ID);
    }
    
    @Test
    public void getHallsByPlaceId_Test_Fail() {
    	Collection<Hall> results = hallRepository.findAllByPlaceId(HallConstants.INVALID_PLACE_ID);
		
		assertEquals(results.size(),0);
    }
    
    @Test
    public void getHallByEventId_Test_Success() {
    	Collection<Hall> results = hallRepository.findAllByEventsId(HallConstants.PERSISTED_HALL_EVENT_ID);
    	
    	assertFalse(results.isEmpty());
    }
    
    @Test
    public void getHallByEventId_Test_Fail() {
    	Collection<Hall> results = hallRepository.findAllByEventsId(HallConstants.INVALID_EVENT_ID);
    	
    	assertTrue(results.isEmpty());
    }
    
    

}
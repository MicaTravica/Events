package com.app.events.serviceimpl.hall;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.app.events.constants.HallConstants;
import com.app.events.constants.PlaceConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.exception.HallMustHaveSector;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.model.Sector;
import com.app.events.repository.HallRepository;
import com.app.events.service.HallService;
import com.app.events.service.PlaceService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class HallServiceImplUnitTest {

    public static Hall HALL_NEW = null;
    public static Hall HALL_UPDATE = null;

    public static Place PLACE = null;
    public static Place INVALID_PLACE = null;
    
    public static Sector SECTOR = null;
    public static Set<Sector> SECTORS = null;
    

    @Autowired
	private HallService hallService;
	
	@LocalServerPort
	private int port;

	@MockBean
    private HallRepository hallRepositoryMocked;

    @MockBean
    private PlaceService placeSerivce;

    @Before
	public void setUp() throws Exception{

        PLACE = new Place(PlaceConstants.PLACE_ID);
        INVALID_PLACE = new Place(PlaceConstants.INVALID_PLACE_ID);
       
        SECTOR = new Sector(SectorConstants.PERSISTED_SECTOR_ID);
        
        HALL_NEW = new Hall(null, HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE, null, new HashSet<>(), new HashSet<>());
        
        HALL_UPDATE = new Hall(HallConstants.PERSISTED_HALL_ID, HallConstants.PERSISTED_HALL_NAME, PLACE, new HashSet<>(), new HashSet<>());
        Optional<Hall> hallOpt = Optional.of(HALL_UPDATE);
        

        Mockito.when(hallRepositoryMocked.findById(HallConstants.PERSISTED_HALL_ID)).thenReturn(hallOpt);
        Mockito.when(hallRepositoryMocked.findById(HallConstants.INVALID_HALL_ID)).thenReturn(Optional.empty());

        Mockito.when(hallRepositoryMocked.save(HALL_NEW)).thenReturn(HALL_NEW);
        Mockito.when(hallRepositoryMocked.save(HALL_UPDATE)).thenReturn(HALL_UPDATE);

        Mockito.when(placeSerivce.findOne(PlaceConstants.PLACE_ID)).thenReturn(PLACE);
        Mockito.when(placeSerivce.findOne(PlaceConstants.INVALID_PLACE_ID))
            .thenThrow(new ResourceNotFoundException("place not found"));
        
        Collection<Hall> halls = new ArrayList<>();
        halls.add(new Hall());
        
        Mockito.when(hallRepositoryMocked.findAllByEventsId(HallConstants.PERSISTED_HALL_EVENT_ID)).thenReturn(halls);
        Mockito.when(hallRepositoryMocked.findAllByPlaceId(HallConstants.PERSISTED_HALL_PLACE_ID)).thenReturn(halls);
        
    }    
    @Test
    public void when_ValidID_thenHallShouldBeFound() throws Exception {
            Hall foundHall = hallService.findOne(HallConstants.PERSISTED_HALL_ID);
            assertNotNull(foundHall);
            assertEquals(HallConstants.PERSISTED_HALL_ID, foundHall.getId());
            assertEquals(HallConstants.PERSISTED_HALL_NAME, foundHall.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenInvalidId_thenThrow_ResourceNotFoundException() throws ResourceNotFoundException
    {
        hallService.findOne(HallConstants.INVALID_HALL_ID);
    }
    
    @Test
    public void findOneAndLoadSectors_Test_Success() throws Exception {
    	Hall foundHall = hallService.findOneAndLoadSectors(HallConstants.PERSISTED_HALL_ID);
        assertNotNull(foundHall);
        assertEquals(HallConstants.PERSISTED_HALL_ID, foundHall.getId());
        assertEquals(HallConstants.PERSISTED_HALL_NAME, foundHall.getName());
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void findOneAndLoadSectors_Test_Fail() throws Exception {
    	hallService.findOneAndLoadSectors(HallConstants.INVALID_HALL_ID);

    }


    @Test(expected = ResourceNotFoundException.class)
    public void WhenCreateInValidHallPlace_thenThrow_ResourceNotFoundException() throws Exception{
        HALL_NEW.setPlace(INVALID_PLACE);
        hallService.create(HALL_NEW);
    }

    @Test(expected = HallMustHaveSector.class)
    public void Create_Test_Fail() throws Exception {
    	HALL_NEW.setPlace(PLACE);
    	HALL_NEW.setSectors(SECTORS);
    	hallService.create(HALL_NEW);
    	
    }
    

    @Test
    public void WhenUpdateValidHallPlace_thenHallShouldBeUpdated() throws Exception{
        HALL_UPDATE.setPlace(PLACE);
        Hall savedHall = hallService.update(HALL_UPDATE);
        assertEquals(savedHall.getName(), HALL_UPDATE.getName());
        assertEquals(savedHall.getPlace().getId(), HALL_UPDATE.getPlace().getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void WhenUpateInValidHallPlace_thenThrow_ResourceNotFoundException() throws Exception{
        HALL_UPDATE.setPlace(INVALID_PLACE);
        hallService.update(HALL_UPDATE);
    }

    @Test(expected = Exception.class)
    public void WhenUpateNotExsistingHall_thenThrow_ResourceNotFoundException() throws Exception{
        HALL_UPDATE.setId(HallConstants.INVALID_HALL_ID);
        hallService.update(HALL_UPDATE);
    }
    
    @Test
    public void getHallsByPlaceId_Test_Success() {
    	Collection<Hall> results = hallRepositoryMocked.findAllByPlaceId(HallConstants.PERSISTED_HALL_PLACE_ID);
		
		assertEquals(results.size(), 1);
    }
    
    @Test
    public void getHallsByPlaceId_Test_Fail() {
    	Collection<Hall> results = hallRepositoryMocked.findAllByPlaceId(HallConstants.INVALID_PLACE_ID);
		
		assertEquals(results.size(),0);
    }
    
    @Test
    public void getHallByEventId_Test_Success() {
    	Collection<Hall> results = hallRepositoryMocked.findAllByEventsId(HallConstants.PERSISTED_HALL_EVENT_ID);
    	
    	assertFalse(results.isEmpty());
    }
    
    @Test
    public void getHallByEventId_Test_Fail() {
    	Collection<Hall> results = hallRepositoryMocked.findAllByEventsId(HallConstants.INVALID_EVENT_ID);
    	
    	assertTrue(results.isEmpty());
    }
    
  

}
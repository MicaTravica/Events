package com.app.events.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Optional;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Place;
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

    public static long HALL_NEW_ID = 1L;
    public static long HALL_UPDATE_ID = 2L;
    public static long INVALID_HALL_ID = -1L;
    
    public static Hall HALL_NEW = null;
    public static Hall HALL_UPDATE = null;

    public static Long PLACE_ID = 1L;
    public static Long INVALID_PLACE_ID = -1L;
    public static Place PLACE = null;
    public static Place INVALID_PLACE = null;

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

        PLACE = new Place(PLACE_ID);
        INVALID_PLACE = new Place(INVALID_PLACE_ID);

        HALL_NEW = new Hall(null, "newHall", null,new HashSet<>(), new HashSet<>());
        HALL_UPDATE = new Hall(HALL_UPDATE_ID, "updateHall", PLACE, new HashSet<>(), new HashSet<>());
        Optional<Hall> hallOpt = Optional.of(HALL_UPDATE);
        Optional<Hall> emptyHallOpt = Optional.empty();

        Mockito.when(hallRepositoryMocked.findById(HALL_UPDATE_ID)).thenReturn(hallOpt);
        Mockito.when(hallRepositoryMocked.findById(INVALID_HALL_ID)).thenReturn(emptyHallOpt);

        Mockito.when(hallRepositoryMocked.save(HALL_NEW)).thenReturn(HALL_NEW);
        Mockito.when(hallRepositoryMocked.save(HALL_UPDATE)).thenReturn(HALL_UPDATE);

        Mockito.when(placeSerivce.findOne(PLACE_ID)).thenReturn(PLACE);
        Mockito.when(placeSerivce.findOne(INVALID_PLACE_ID))
            .thenThrow(new ResourceNotFoundException("place not found"));

        //Mockito.doNothing().when(hallRepositoryMocked.deleteById(HALL_ID));
    }    
    @Test
    public void whenValidId_thenHallShouldBeFound() throws Exception {
            Hall foundHall = hallService.findOne(HALL_UPDATE_ID);
            assertEquals(HALL_UPDATE_ID, foundHall.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenInvalidId_thenThrow_ResourceNotFoundException() throws ResourceNotFoundException
    {
        hallService.findOne(INVALID_HALL_ID);
    }

    /*
        problem how to set id new Hall when is created...
        also is global object problem, due atribute changes?
    */
    @Test
    public void WhenCreateValidHall_thenHallShouldBeSaved() throws Exception{
        HALL_NEW.setPlace(PLACE);
        Hall savedHall = hallService.create(HALL_NEW);
        assertEquals(savedHall.getName(), HALL_NEW.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void WhenCreateInValidHallPlace_thenThrow_ResourceNotFoundException() throws Exception{
        HALL_NEW.setPlace(INVALID_PLACE);
        Hall savedHall = hallService.create(HALL_NEW);
        assertEquals(savedHall.getName(), HALL_NEW.getName());
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
        Hall savedHall = hallService.update(HALL_UPDATE);
        assertEquals(savedHall.getName(), HALL_UPDATE.getName());
        assertEquals(savedHall.getPlace().getId(), HALL_UPDATE.getPlace().getId());
    }

    @Test(expected = Exception.class)
    public void WhenUpateNotExsistingHall_thenThrow_ResourceNotFoundException() throws Exception{
        HALL_UPDATE.setId(INVALID_HALL_ID);
        hallService.update(HALL_UPDATE);
    }



}
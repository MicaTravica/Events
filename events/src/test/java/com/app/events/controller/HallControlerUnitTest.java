package com.app.events.controller;

import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.service.HallService;
import com.app.events.dto.HallDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.mapper.HallMapper;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

/*
    how to mock HallMapper ---> it is static method..
*/ 





@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)

// @ActiveProfiles("test")
// @TestPropertySource(locations = "classpath:application-test.properties")


public class HallControlerUnitTest {

    public static long HALL_FIND_ID = 1L;
    public static long INVALID_HALL_ID = -1L;
    
    public static long PLACE_ID = 1L;

    @Autowired
    private TestRestTemplate restTemplate;
    
    @MockBean
    private HallService hallService;

    @Before
    public void setUp() throws Exception{

        Mockito.when(hallService.findOne(HALL_FIND_ID)).thenReturn
            (new Hall(HALL_FIND_ID, "findHall", new Place(PLACE_ID),new HashSet<>(), new HashSet<>()));
        
        Mockito.when(hallService.findOne(INVALID_HALL_ID)).thenThrow(new ResourceAccessException("Hall not found"));
    }


    @Ignore
    @Test
    @WithMockUser(username = "dusan", password = "cao", roles = {"REGULAR"})
    public void whenValidId_thenFoundHall() throws Exception{
        ResponseEntity<HallDTO> responseEntity =
	        restTemplate.getForEntity("/api/hall/"+ HALL_FIND_ID, HallDTO.class);
        Hall hall = HallMapper.toHall(responseEntity.getBody());

		assertEquals(hall.getId(), HALL_FIND_ID);
    }

    @Ignore
    @Test(expected = ResourceNotFoundException.class)
    @WithMockUser(username = "user1", password = "pwd", roles = {"REGULAR"})
    public void whenInvalidId_thenThowException() throws Exception{
        ResponseEntity<HallDTO> responseEntity =
            restTemplate.getForEntity("/api/hall/" + INVALID_HALL_ID, HallDTO.class);
            

        
        Hall hall = HallMapper.toHall(responseEntity.getBody());

		assertEquals(hall.getId(), HALL_FIND_ID);
    }



}
package com.app.events.controller;

import com.app.events.constants.HallConstants;
import com.app.events.constants.PlaceConstants;
import com.app.events.constants.SeatConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.HallDTO;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.PlaceDTO;
import com.app.events.mapper.HallMapper;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.model.Seat;
import com.app.events.repository.HallRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")

public class HallControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String authTokenAdmin = "";

    @Autowired
    private HallRepository hallRepository;

    @Before
    public void login() throws Exception{
        LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
        authTokenAdmin = response.getBody();
    }

    @Test()
    public void foundHall_when_Valid_ID_thenShouldFindHall() throws Exception{
        
        URI uri = new URI(HallConstants.URI_PREFIX + HallConstants.PERSISTED_HALL_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<HallDTO> res = restTemplate.exchange(uri, HttpMethod.GET, req, HallDTO.class);

        assertNotNull(res.getBody());
        assertEquals(HallConstants.PERSISTED_HALL_ID, res.getBody().getId());
        assertEquals(HallConstants.PERSISTED_HALL_NAME, res.getBody().getName());
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }

    @Test
    public void foundHall_when_Invalid_ID_then_return_NotFound() throws Exception{
        
        URI uri = new URI(HallConstants.URI_PREFIX + HallConstants.INVALID_HALL_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.GET, req, String.class);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void addHall_when_ValidHall_then_HallShouldBeAdded() throws Exception
    {
        int sizeBeforeInsert = hallRepository.findAll().size();

        Hall hall = new Hall(
            null,
            HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE,
            new Place(PlaceConstants.PERSISTED_PLACE_ID),
            new HashSet<>(), new HashSet<>());
        
        HallDTO content = HallMapper.toDTO(hall);

        URI uri = new URI(HallConstants.URI_PREFIX);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<HallDTO> req = new HttpEntity<>(content, headers);

        ResponseEntity<HallDTO> res = restTemplate.exchange(uri, HttpMethod.POST, req, HallDTO.class);

        List<Hall> afterInsert = hallRepository.findAll();
        Hall addedHall = afterInsert.get(afterInsert.size()-1);

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());

        assertEquals(HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE, res.getBody().getName());
        assertEquals(sizeBeforeInsert + 1, afterInsert.size());

        hallRepository.delete(addedHall);
    }

    @Test
    public void addHall_when_InvalidPlace_thenHallShould_NotBeAdded() throws Exception
    {
        int sizeBeforeInsert = hallRepository.findAll().size();

        Hall hall = new Hall(
            null,HallConstants.VALID_HALL_NAME_FOR_PERSISTANCE,
            new Place(PlaceConstants.INVALID_PLACE_ID),
            new HashSet<>(), new HashSet<>());
        
        HallDTO content = HallMapper.toDTO(hall);

        URI uri = new URI(HallConstants.URI_PREFIX);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<HallDTO> req = new HttpEntity<>(content, headers);

        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.POST, req, String.class);

        int afterInsertSize = hallRepository.findAll().size();

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertEquals(sizeBeforeInsert, afterInsertSize);
    }

    @Test
    public void update_Test_Success() throws Exception
    {
        
        HallDTO content = new HallDTO(
                HallConstants.PERSISTED_HALL_ID,
                HallConstants.PERSISTED_HALL_NAME,
                new PlaceDTO(PlaceConstants.PERSISTED_PLACE_ID),
                new HashSet<>());

        URI uri = new URI(HallConstants.URI_PREFIX);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<HallDTO> req = new HttpEntity<>(content, headers);

        ResponseEntity<HallDTO> res = restTemplate.exchange(uri, HttpMethod.PUT, req, HallDTO.class);

        List<Hall> afterInsert = hallRepository.findAll();
        Hall addedHall = afterInsert.get(afterInsert.size()-1);

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.OK, res.getStatusCode());

        assertEquals(HallConstants.PERSISTED_HALL_NAME, res.getBody().getName());

        hallRepository.delete(addedHall);
    }
    
    @Test
    public void update_Test_Fail() throws Exception
    {
        
        HallDTO content = new HallDTO(
                HallConstants.INVALID_HALL_ID);

        URI uri = new URI(HallConstants.URI_PREFIX);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<HallDTO> req = new HttpEntity<>(content, headers);

        ResponseEntity<HallDTO> res = restTemplate.exchange(uri, HttpMethod.PUT, req, HallDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());

        
    }
    
    @Test
    public void delete_Test_Fail() throws Exception
    {

        Long hall = HallConstants.PERSISTED_HALL_ID;
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<Long> req = new HttpEntity<>(hall, headers);

        ResponseEntity<Seat> res = restTemplate.exchange("/api/hall/" + hall , HttpMethod.DELETE, req, Seat.class);


        assertEquals(HttpStatus.NO_CONTENT, res.getStatusCode());
    }


}
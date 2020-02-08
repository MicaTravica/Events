package com.app.events.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import java.net.URI;


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

import com.app.events.constants.PlaceConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.PlaceDTO;
import com.app.events.mapper.PlaceMapper;
import com.app.events.model.Place;
import com.app.events.repository.PlaceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PlaceControllerIntegrationTest {
	
	public static URI uri;
    public static HttpHeaders headers;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authTokenAdmin = "";

    @Autowired
    private PlaceRepository placeRepository;
    
    @Before
    public void login() throws Exception{
        LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
        authTokenAdmin = response.getBody();
    }

    
    @Test
    public void findPlace_TestSuccess() throws Exception{
        
        URI uri = new URI(PlaceConstants.URI_PREFIX + SectorConstants.PERSISTED_SECTOR_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<PlaceDTO> res = restTemplate.exchange(uri, HttpMethod.GET, req, PlaceDTO.class);

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(PlaceConstants.PERSISTED_PLACE_ID, res.getBody().getId());
    }
    
    
    
    
    @Test
    public void finddPlace_TestFail() throws Exception{
        
    	URI uri = new URI(PlaceConstants.URI_PREFIX + PlaceConstants.INVALID_PLACE_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.GET, req, String.class);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());

    }
   

    @Test
    public void add_Test_Fail() throws Exception
    {
    	 int sizeBeforeInsert = placeRepository.findAll().size();

         Place place = new Place(
             null,
             PlaceConstants.PERSISTED_HALL_NAME,
             null,
             PlaceConstants.PERSISTED_PLACE_LATITUDE,
             PlaceConstants.PERSISTED_PLACE_LONGITUDE);
        
         PlaceDTO content = PlaceMapper.toDTO(place);

         URI uri = new URI(PlaceConstants.URI_PREFIX);
         HttpHeaders headers = new HttpHeaders();
         headers.add("Authorization", "Bearer " + this.authTokenAdmin);
         HttpEntity<PlaceDTO> req = new HttpEntity<>(content, headers);

         ResponseEntity<PlaceDTO> res = restTemplate.exchange(uri, HttpMethod.POST, req, PlaceDTO.class);

        int afterInsertSize = placeRepository.findAll().size();

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
        assertEquals(sizeBeforeInsert, afterInsertSize);
    }

}

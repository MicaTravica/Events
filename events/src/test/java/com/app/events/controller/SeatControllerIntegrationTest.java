package com.app.events.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import com.app.events.constants.SeatConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.SeatDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SeatControllerIntegrationTest {
	public static URI uri;
    public static HttpHeaders headers;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authTokenAdmin = "";

   
    
    @Before
    public void login() throws Exception{
        LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
        authTokenAdmin = response.getBody();
    }

    @Test()
    public void findSeat_TestSuccess() throws Exception{
        
        URI uri = new URI(SeatConstants.URI_PREFIX + SeatConstants.PERSISTED_SEAT_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<SeatDTO> res = restTemplate.exchange(uri, HttpMethod.GET, req, SeatDTO.class);

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(SeatConstants.PERSISTED_SEAT_ID, res.getBody().getId());
        assertEquals(SeatConstants.PERSISTED_SEAT_COLUMN, res.getBody().getSeatColumn());
        assertEquals(SeatConstants.PERSISTED_SEAT_ROW, res.getBody().getSeatRow());
    }
    
    @Test
	 public void findSeat_when_Invalid_ID_then_return_NotFound() throws Exception{
	        
		 URI uri = new URI(SeatConstants.URI_PREFIX + SeatConstants.INVALID_SEAT_ID);
	     HttpHeaders headers = new HttpHeaders();
	     headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	     HttpEntity<String> req = new HttpEntity<>(headers);

	     ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.GET, req, String.class);

	     assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	 }
    
    
 
     
     
     


    
   
}

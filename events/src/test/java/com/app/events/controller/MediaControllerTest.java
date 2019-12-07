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

import com.app.events.constants.EventConstants;
import com.app.events.constants.MediaConstants;
import com.app.events.constants.UserConstans;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.MediaDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MediaControllerTest {

    private String authTokenAdmin = "";
    
	@Autowired
    private TestRestTemplate restTemplate;
    
    @Before
    public void login() throws Exception{
        LoginDTO loginDto = new LoginDTO(UserConstans.DB_ADMIN_USERNAME, UserConstans.DB_ADMIN_PASSWORD);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
        authTokenAdmin = response.getBody();
    }

    @Test
    public void testGetOneMedia() throws Exception {
    	URI uri = new URI(MediaConstants.URL_PREFIX + "/" + MediaConstants.PERSISTED_MEDIA_ID);
    	
        ResponseEntity<MediaDTO> response = restTemplate.getForEntity(uri, MediaDTO.class);
        MediaDTO found = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertNotNull(found);
	    assertEquals(MediaConstants.PERSISTED_MEDIA_ID, found.getId());
	    assertEquals(MediaConstants.PERSISTED_MEDIA_PATH, found.getPath());
    }
    
    @Test
    public void testGetOneMediaThrow() throws Exception {
    	URI uri = new URI(MediaConstants.URL_PREFIX + "/" + MediaConstants.INVALID_MEDIA_ID);
    	
        ResponseEntity<Object> response = restTemplate.getForEntity(uri, Object.class);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());        
    }
    
    @Test
    public void testGetEventMedias() throws Exception {
    	URI uri = new URI(MediaConstants.URL_PREFIX + "/event/" + EventConstants.PERSISTED_EVENT_ID);
    	
        ResponseEntity<MediaDTO[]> response = restTemplate.getForEntity(uri, MediaDTO[].class);
        MediaDTO[] found = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertNotNull(found);
	    assertEquals(2, found.length);
    }
    
    @Test
    public void testCreateMedia() throws Exception {
    	URI uri = new URI(MediaConstants.URL_PREFIX);

    	MediaDTO media = new MediaDTO(null, MediaConstants.VALID_MEDIA_PATH_FOR_PERSISTANCE, EventConstants.PERSISTED_EVENT_ID2);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<MediaDTO> req = new HttpEntity<>(media, headers);
        
        ResponseEntity<MediaDTO> response = restTemplate.exchange(uri, HttpMethod.POST, req, MediaDTO.class);
        MediaDTO created = response.getBody();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertNotNull(created);
	    assertEquals(EventConstants.PERSISTED_EVENT_ID2, created.getEventId());
	    assertEquals(MediaConstants.VALID_MEDIA_PATH_FOR_PERSISTANCE, created.getPath());
	        
    }
    
    @Test
    public void testDeleteMedia() throws Exception {
    	URI uri = new URI(MediaConstants.URL_PREFIX + '/' + MediaConstants.VALID_MEDIA_ID_FOR_DELETE);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<MediaDTO> req = new HttpEntity<>(headers);
        
        ResponseEntity<Object> response = restTemplate.exchange(uri, HttpMethod.DELETE, req, Object.class);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());      
    }
}

package com.app.events.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.net.URI;
import java.util.HashSet;

import javax.transaction.Transactional;

import com.app.events.constants.SectorCapacityConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.SectorCapacityDTO;
import com.app.events.dto.SectorDTO;
import com.app.events.repository.SectorCapacityRepository;

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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SectorCapacityControllerIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SectorCapacityControllerIntegrationTest {

    private String authTokenAdmin = "";

	@Autowired
	private TestRestTemplate restTemplate;

    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;
    
	@Before
	public void login() throws Exception {
		LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
		ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
		authTokenAdmin = response.getBody();
    }
    
    @Test
    public void findOneById_when_validID_then_return_SectorCapacityDTO() throws Exception
    {
        URI uri = new URI(SectorCapacityConstants.URI_PREFIX + SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID);

		ResponseEntity<SectorCapacityDTO> response = restTemplate.getForEntity(uri, SectorCapacityDTO.class);
		SectorCapacityDTO found = response.getBody();

        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID, found.getId());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY, found.getCapacity());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE, found.getFree());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID, found.getSector().getId());
    }

    @Test
    public void findOneById_when_InvalidID_then_returnNotFound() throws Exception
    {
        URI uri = new URI(SectorCapacityConstants.URI_PREFIX + SectorCapacityConstants.INVALID_SECTOR_CAPACITY_ID);

		ResponseEntity<SectorCapacityDTO> response = restTemplate.getForEntity(uri, SectorCapacityDTO.class);
        SectorCapacityDTO found = response.getBody();
        
        assertEquals(null, found.getId());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    @Transactional
    @Rollback(true)
    public void create_when_validDataPassedIn_then_addNew() throws Exception
    {
        int sizeBeforeSave = sectorCapacityRepository.findAll().size();

        SectorCapacityDTO sc = new SectorCapacityDTO(
                null,
                new SectorDTO(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID),
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE);

        URI uri = new URI(SectorCapacityConstants.URI_PREFIX);
        HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<SectorCapacityDTO> req = new HttpEntity<>(sc,headers);
        

		ResponseEntity<SectorCapacityDTO> response = restTemplate.exchange(uri, HttpMethod.POST,req, SectorCapacityDTO.class);
        SectorCapacityDTO found = response.getBody();

        int sizeAfterSave = sectorCapacityRepository.findAll().size();

        assertNotNull(found);
        assertEquals(sizeAfterSave, sizeBeforeSave + 1);
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                    found.getCapacity());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE,
                    found.getFree());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID,
                    found.getSector().getId());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Transactional
    @Rollback(true)
    public void create_when_invalidSectorID_then_throwResourceNotFound() throws Exception
    {
        SectorCapacityDTO sc = new SectorCapacityDTO(
            null,
            new SectorDTO(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_INVALID_ID),
            SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
            SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE);
        
        URI uri = new URI(SectorCapacityConstants.URI_PREFIX);
        HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<SectorCapacityDTO> req = new HttpEntity<>(sc,headers);
        
        ResponseEntity<SectorCapacityDTO> response = restTemplate.postForEntity(uri, req, SectorCapacityDTO.class);
        SectorCapacityDTO found = response.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
}
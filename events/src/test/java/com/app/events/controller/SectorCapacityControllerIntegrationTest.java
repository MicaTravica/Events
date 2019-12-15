package com.app.events.controller;

import static org.junit.Assert.assertEquals;
import java.net.URI;

import com.app.events.constants.SectorCapacityConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.SectorCapacityDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.repository.SectorCapacityRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SectorCapacityControllerIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SectorCapacityControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
        
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

		ResponseEntity<ResourceNotFoundException> response = restTemplate.getForEntity(uri, ResourceNotFoundException.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
}
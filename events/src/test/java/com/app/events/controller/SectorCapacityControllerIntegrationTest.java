package com.app.events.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.List;

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


import com.app.events.constants.SectorCapacityConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.SectorCapacityDTO;
import com.app.events.dto.SectorDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.SectorCapacity;
import com.app.events.repository.SectorCapacityRepository;

/**
 * SectorCapacityControllerIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SectorCapacityControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
    private String authTokenAdmin = "";
    
    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;
        
    @Before
    public void login() throws Exception{
        LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
        authTokenAdmin = response.getBody();
    }

    
    @Test
    public void findOneById_when_validID_then_return_SectorCapacityDTO() throws Exception
    {
        //TODO: sacuvati neki SC i onda testrati da li njega nalazi..

        URI uri = new URI(SectorCapacityConstants.URI_PREFIX + SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID);

		ResponseEntity<SectorCapacityDTO> response = restTemplate.getForEntity(uri, SectorCapacityDTO.class);
		SectorCapacityDTO found = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());

		
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID, found.getId());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY, found.getCapacity());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE, found.getFree());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID, found.getSector().getId());
    }
    
    @Test
    public void findOneById_Fail() throws Exception
    {
        URI uri = new URI(SectorCapacityConstants.URI_PREFIX + SectorCapacityConstants.INVALID_SECTOR_CAPACITY_ID);

		ResponseEntity<SectorCapacityDTO> response = restTemplate.getForEntity(uri, SectorCapacityDTO.class);
		SectorCapacityDTO found = response.getBody();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


 
    
    @Test
    public void creat_Test_Success() throws Exception
    {
        int sizeBeforeInsert = sectorCapacityRepository.findAll().size();

        SectorCapacityDTO sc = new SectorCapacityDTO(null,
        		new SectorDTO(SectorConstants.PERSISTED_SECTOR_ID),
        		SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
        		SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE
        		);
        

        URI uri = new URI(SectorCapacityConstants.URI_PREFIX);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<SectorCapacityDTO> req = new HttpEntity<>(sc, headers);

        ResponseEntity<SectorCapacityDTO> res = restTemplate.exchange(uri, HttpMethod.POST, req, SectorCapacityDTO.class);

        List<SectorCapacity> afterInsert = sectorCapacityRepository.findAll();
        SectorCapacity addedSector = afterInsert.get(afterInsert.size()-1);

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());

        assertEquals(sizeBeforeInsert + 1, afterInsert.size());

        sectorCapacityRepository.delete(addedSector);
    }
 
    
    @Test
    public void creat_Test_Fail() throws Exception
    {
        int sizeBeforeInsert = sectorCapacityRepository.findAll().size();

        SectorCapacityDTO sc = new SectorCapacityDTO(null,
        		new SectorDTO(SectorConstants.INVALID_SECTOR_ID),
        		SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
        		SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE
        		);
        

        URI uri = new URI(SectorCapacityConstants.URI_PREFIX);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<SectorCapacityDTO> req = new HttpEntity<>(sc, headers);

        ResponseEntity<SectorCapacityDTO> res = restTemplate.exchange(uri, HttpMethod.POST, req, SectorCapacityDTO.class);

        List<SectorCapacity> afterInsert = sectorCapacityRepository.findAll();

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());

    }
 
 
    
    
 
   
    
}
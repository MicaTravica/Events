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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.events.constants.PriceListConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.PriceListDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.PriceList;
import com.app.events.repository.PriceListRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class PriceListControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
    private String authTokenAdmin = "";
    
    @Autowired
    private PriceListRepository priceListRepository;
        
    @Before
    public void login() throws Exception{
        LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
        authTokenAdmin = response.getBody();
    }

    
    @Test
    @Transactional
	@Rollback(true)
    public void findOneById_Test_Success() throws Exception
    {
    	//TODO: osigurati se da u bazi postoji to sto trazi
    	URI uri = new URI(PriceListConstants.URI_PREFIX );

		ResponseEntity<PriceListDTO> response = restTemplate.getForEntity(uri + "/" + PriceListConstants.PERSISTED_PL_ID, PriceListDTO.class);
		PriceListDTO found = response.getBody();

        assertEquals(PriceListConstants.PERSISTED_PL_ID, found.getId());
        assertEquals(PriceListConstants.PERSISTED_SECTOR_ID, found.getSectorId());
        assertEquals(PriceListConstants.PERSISTED_EVENT_ID, found.getEventId());
    }

    @Test
    @Transactional
	@Rollback(true)
    public void findOneById_Test_Fail() throws Exception
    {
    	URI uri = new URI(PriceListConstants.URI_PREFIX );

		ResponseEntity<ResourceNotFoundException> response = restTemplate.getForEntity(uri + "/" + PriceListConstants.INVALID_PL_ID, ResourceNotFoundException.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    @Transactional
	@Rollback(true)
    public void creat_Test_Success() throws Exception
    {
        int sizeBeforeInsert = priceListRepository.findAll().size();

        PriceListDTO sc = new PriceListDTO(null,
        		PriceListConstants.PERSISTED_PRICE,
        		PriceListConstants.PERSISTED_EVENT_ID,
        		PriceListConstants.PERSISTED_SECTOR_ID
        		);
        

    	URI uri = new URI(PriceListConstants.URI_PREFIX );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<PriceListDTO> req = new HttpEntity<>(sc, headers);

        ResponseEntity<PriceListDTO> res = restTemplate.exchange(uri, HttpMethod.POST, req, PriceListDTO.class);

        List<PriceList> afterInsert = priceListRepository.findAll();
        PriceList addedSector = afterInsert.get(afterInsert.size()-1);

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());

        assertEquals(sizeBeforeInsert + 1, afterInsert.size());

        priceListRepository.delete(addedSector);
    }
 
    @Test
    @Transactional
	@Rollback(true)
    public void creat_Test_Fail() throws Exception
    {

    	 PriceListDTO sc = new PriceListDTO(null,
         		PriceListConstants.PERSISTED_PRICE,
         		PriceListConstants.PERSISTED_EVENT_ID,
         		PriceListConstants.INVALID_PL_ID
         		);
         

     	URI uri = new URI(PriceListConstants.URI_PREFIX );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<PriceListDTO> req = new HttpEntity<>(sc, headers);

        ResponseEntity<PriceListDTO> res = restTemplate.exchange(uri, HttpMethod.POST, req, PriceListDTO.class);


        assertNotNull(res.getBody());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());

    }
    
    
    @Test
    @Transactional
	@Rollback(true)
    public void update_Test_Success() throws Exception
    {
    	PriceListDTO sc = new PriceListDTO(
    			PriceListConstants.PERSISTED_PL_ID,
         		PriceListConstants.NEW_PRICE,
         		PriceListConstants.PERSISTED_EVENT_ID,
         		PriceListConstants.INVALID_PL_ID
         		);
         

    	
    	URI uri = new URI(PriceListConstants.URI_PREFIX );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<PriceListDTO> req = new HttpEntity<>(sc, headers);

        ResponseEntity<PriceListDTO> res = restTemplate.exchange(uri, HttpMethod.PUT, req, PriceListDTO.class);


        assertNotNull(res.getBody());
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
    
    @Test
    @Transactional
	@Rollback(true)
    public void update_Test_Fail() throws Exception
    {
    	PriceListDTO sc = new PriceListDTO(
    			PriceListConstants.INVALID_PL_ID,
         		PriceListConstants.NEW_PRICE,
         		PriceListConstants.PERSISTED_EVENT_ID,
         		PriceListConstants.INVALID_PL_ID
         		);
         

    	
    	URI uri = new URI(PriceListConstants.URI_PREFIX );

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<PriceListDTO> req = new HttpEntity<>(sc, headers);

        ResponseEntity<PriceListDTO> res = restTemplate.exchange(uri, HttpMethod.PUT, req, PriceListDTO.class);


        assertNotNull(res.getBody());
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }
    
    
    
}

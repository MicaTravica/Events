package com.app.events.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.HashSet;
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

import com.app.events.constants.HallConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.constants.UserConstants;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.SectorDTO;
import com.app.events.mapper.SectorMapper;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.SectorRepository;

/**
 * SectorControllerIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SectorControllerIntegrationTest {

    public static URI uri;
    public static HttpHeaders headers;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authTokenAdmin = "";

    @Autowired
    private SectorRepository sectorRepository;

    @Before
    public void login() throws Exception{
        LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
        authTokenAdmin = response.getBody();
    }

    @Test()
    public void finddSector_when_Valid_ID_thenShouldFindSector() throws Exception{
        
        URI uri = new URI(SectorConstants.URI_PREFIX + SectorConstants.PERSISTED_SECTOR_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<SectorDTO> res = restTemplate.exchange(uri, HttpMethod.GET, req, SectorDTO.class);

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(SectorConstants.PERSISTED_SECTOR_ID, res.getBody().getId());
    }

    @Test
    public void finddSector_when_Invalid_ID_then_return_NotFound() throws Exception{
        
        URI uri = new URI(SectorConstants.URI_PREFIX + SectorConstants.INVALID_SECTOR_ID);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<String> req = new HttpEntity<>(headers);

        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.GET, req, String.class);

        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }

    @Test
    public void addSector_when_ValidSector_then_SectorShouldBeAdded() throws Exception
    {
        int sizeBeforeInsert = sectorRepository.findAll().size();

        Sector sector = new Sector(null,
                    SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE,
                    SectorConstants.PERSISTED_SECTOR_COLUMNS,
                    SectorConstants.PERSISTED_SECTOR_ROWS,
                    new Hall(HallConstants.PERSISTED_HALL_ID),
                    new HashSet<>(),new HashSet<>(),new HashSet<>());

        SectorDTO content = SectorMapper.toDTO(sector);

        URI uri = new URI(SectorConstants.URI_PREFIX);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
        HttpEntity<SectorDTO> req = new HttpEntity<>(content, headers);

        ResponseEntity<SectorDTO> res = restTemplate.exchange(uri, HttpMethod.POST, req, SectorDTO.class);

        List<Sector> afterInsert = sectorRepository.findAll();
        Sector addedSector = afterInsert.get(afterInsert.size()-1);

        assertNotNull(res.getBody());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());

        assertEquals(SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE, res.getBody().getName());
        assertEquals(sizeBeforeInsert + 1, afterInsert.size());
        checkCreatedSectorDTO(addedSector, res.getBody());

        sectorRepository.delete(addedSector);
    }
    
    private void checkCreatedSectorDTO(Sector sector, SectorDTO sectorDto) {
        assertEquals(sector.getId(), sectorDto.getId());
        assertEquals(sector.getName(), sectorDto.getName());
        
        assertEquals(sector.getSectorColumns(), sectorDto.getSectorColumns());
        assertEquals(sector.getSectorRows(), sectorDto.getSectorRows());
        assertEquals(sector.getHall().getId(), sectorDto.getHallID());
    }




}
package com.app.events.serviceimpl.sector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import com.app.events.constants.HallConstans;
import com.app.events.constants.SectorConstants;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.HallRepository;
import com.app.events.repository.SectorRepository;
import com.app.events.serviceimpl.SectorServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SectorServiceImplUnitTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SectorServiceImplUnitTest {

    public static Sector NEW_SECTOR = null;
    public static Sector INVALID_SECTOR = null;
    public static Sector SAVED_SECTOR = null;
    

    @Autowired
    private SectorServiceImpl sectorService;

    @MockBean
    private SectorRepository sectorRepository;

    @MockBean
    private HallRepository hallRepository;

    @Before
	public void setUp() throws Exception{

        NEW_SECTOR = new Sector(
                null, 
                SectorConstants.PERSISTED_SECTOR_NAME,
                SectorConstants.PERSISTED_SECTOR_ROWS,
                SectorConstants.PERSISTED_SECTOR_COLUMNS);

        INVALID_SECTOR = new Sector(SectorConstants.INVALID_SECTOR_ID);

        SAVED_SECTOR = new Sector(
            SectorConstants.PERSISTED_SECTOR_ID, 
            SectorConstants.PERSISTED_SECTOR_NAME,
            SectorConstants.PERSISTED_SECTOR_ROWS,
            SectorConstants.PERSISTED_SECTOR_COLUMNS);

        when(sectorRepository.findById(SectorConstants.PERSISTED_SECTOR_ID)).thenReturn(Optional.of(SAVED_SECTOR));
        when(sectorRepository.findById(SectorConstants.INVALID_SECTOR_ID)).thenReturn(Optional.empty());
        
        when(sectorRepository.save(NEW_SECTOR)).thenReturn(NEW_SECTOR);
        when(sectorRepository.save(SAVED_SECTOR)).thenReturn(SAVED_SECTOR);

        when(hallRepository.findById(HallConstans.INVALID_HALL_ID)).thenReturn(Optional.empty());
        when(hallRepository.findById(HallConstans.PERSISTED_HALL_ID)).thenReturn(Optional.of(
            new Hall(
                HallConstans.PERSISTED_HALL_ID,
                HallConstans.PERSISTED_HALL_NAME,
                null, new HashSet<>(), new HashSet<>()
            )
        ));
    }   
    @Test
    public void findSector_when_ValidID_then_Sector_ShouldBeFound() throws Exception {
            Sector foundSector = sectorService.findOne(SectorConstants.PERSISTED_SECTOR_ID);
            assertNotNull(foundSector);
            assertEquals(SectorConstants.PERSISTED_SECTOR_ID, foundSector.getId());
            assertEquals(SectorConstants.PERSISTED_SECTOR_NAME, foundSector.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findSector_when_InvalidId_thenThrow_ResourceNotFoundException() throws ResourceNotFoundException
    {
        sectorService.findOne(SectorConstants.INVALID_SECTOR_ID);
    }

    @Test
    public void createSector_when_CreateValidSector_thenSectorShouldBeSaved() throws Exception{

        Hall hall = new Hall(HallConstans.PERSISTED_HALL_ID);

        NEW_SECTOR.setHall(hall);
        Sector savedSector = sectorService.create(NEW_SECTOR);

        assertEquals(NEW_SECTOR.getName(), savedSector.getName());
        assertEquals(NEW_SECTOR.getHall().getId(), savedSector.getHall().getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createSector_when_InValidSectorHall_thenThrow_ResourceNotFoundException() throws Exception{
        Hall hall = new Hall(HallConstans.INVALID_HALL_ID);
        NEW_SECTOR.setHall(hall);
        sectorService.create(NEW_SECTOR);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateSector_when_SectorDoesntExist() throws Exception
    {
        sectorService.update(new Sector(SectorConstants.INVALID_SECTOR_ID));
    }
}
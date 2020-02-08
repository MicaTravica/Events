package com.app.events.serviceimpl.sector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.EventConstants;
import com.app.events.constants.HallConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.ResourceNullNumber;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.HallRepository;
import com.app.events.repository.SectorRepository;
import com.app.events.serviceimpl.SectorServiceImpl;

/**
 * SectorServiceImplUnitTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SectorServiceImplUnitTest {

    public static Sector NEW_SECTOR = null;
    public static Sector INVALID_SECTOR = null;
    public static Sector SAVED_SECTOR = null;
    public static Sector SECTOR3 = null;
    

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
        
        SECTOR3 = new Sector(null,
                SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE,
                SectorConstants.INVALID_SECTOR_ROWS, 
                SectorConstants.INVALID_SECTOR_COLUMNS);

        when(sectorRepository.findById(SectorConstants.PERSISTED_SECTOR_ID)).thenReturn(Optional.of(SAVED_SECTOR));
        when(sectorRepository.findById(SectorConstants.INVALID_SECTOR_ID)).thenReturn(Optional.empty());
        
        when(sectorRepository.save(NEW_SECTOR)).thenReturn(NEW_SECTOR);
        when(sectorRepository.save(SAVED_SECTOR)).thenReturn(SAVED_SECTOR);

        when(hallRepository.findById(HallConstants.INVALID_HALL_ID)).thenReturn(Optional.empty());
        when(hallRepository.findById(HallConstants.PERSISTED_HALL_ID)).thenReturn(Optional.of(
            new Hall(
                HallConstants.PERSISTED_HALL_ID,
                HallConstants.PERSISTED_HALL_NAME,
                null, new HashSet<>(), new HashSet<>()
            )
        ));
    }   
    @Test
    public void findSector_when_ValidID_then_Sector_ShouldBeFound() throws Exception {
        Sector foundSector = sectorService.findOne(SAVED_SECTOR.getId());
        assertNotNull(foundSector);
        assertEquals(SAVED_SECTOR.getId(), foundSector.getId());
        assertEquals(SAVED_SECTOR.getName(), foundSector.getName());
    }
  

    @Test(expected = ResourceNotFoundException.class)
    public void findSector_when_InvalidId_thenThrow_ResourceNotFoundException() throws ResourceNotFoundException
    {
        sectorService.findOne(SectorConstants.INVALID_SECTOR_ID);
    }

    @Test(expected = ResourceNullNumber.class)
    public void createSector_Test_Fail() throws Exception
    {
        sectorService.create(SECTOR3);
    }
    

    @Test(expected = ResourceNotFoundException.class)
    public void createSector_when_InValidSectorHall_thenThrow_ResourceNotFoundException() throws Exception{
        Hall hall = new Hall(HallConstants.INVALID_HALL_ID);
        NEW_SECTOR.setHall(hall);
        sectorService.create(NEW_SECTOR);
    }

    
    @Test(expected = ResourceNotFoundException.class)
    public void updateSector_when_SectorDoesntExist() throws Exception
    {
        sectorService.update(new Sector(SectorConstants.INVALID_SECTOR_ID));
    }
    
    

	@Test 
	public void findAllByHallId_Test_Fail() {
		Collection<Sector> result = sectorRepository.findAllByHallId(HallConstants.INVALID_HALL_ID);
		assertEquals(result.size(),0);

	}

	
	@Test 
	public void findAllByHallIdAndEventId_Test_Fail() {
		
		Collection<Sector> result = sectorRepository.findAllByHallIdAndEventId(HallConstants.INVALID_HALL_ID, EventConstants.INVALID_EVENT_ID);
		assertEquals(result.size(),0);
	}
    

    
  
}
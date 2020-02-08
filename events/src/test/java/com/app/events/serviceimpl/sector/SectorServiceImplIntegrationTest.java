package com.app.events.serviceimpl.sector;

import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.SectorRepository;
import com.app.events.serviceimpl.SectorServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Collection;

import com.app.events.constants.*;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.ResourceNullNumber;
import com.app.events.exception.TicketBoughtOrReservedException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
/**
 * SectorServiceImplIntegrationTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SectorServiceImplIntegrationTest {

    @Autowired
    private SectorServiceImpl sectorServiceImpl;

    @Autowired
    private SectorRepository sectorRepository;

    @Test
    public void findOne_when_ID_valid_should_returnSector() throws Exception
    {
        Sector foundSector = sectorServiceImpl.findOne(SectorConstants.PERSISTED_SECTOR_ID);
        assertEquals(SectorConstants.PERSISTED_SECTOR_ID, foundSector.getId());
        assertEquals(SectorConstants.PERSISTED_SECTOR_NAME, foundSector.getName());
    }

    @Test(expected = ResourceNotFoundException.class )
    public void findOne_when_ID_NotValid_should_thow_ResourceNotFoundException() throws Exception
    {
        sectorServiceImpl.findOne(SectorConstants.INVALID_SECTOR_ID);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createSector_when_hallID_notValid_should_throw_ResourceNotFoundException() throws Exception
    {
        Sector s = new Sector(null, SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE,
                                    SectorConstants.PERSISTED_SECTOR_ROWS, 
                                    SectorConstants.PERSISTED_SECTOR_COLUMNS);
        s.setHall(new Hall(HallConstants.INVALID_HALL_ID));
        sectorServiceImpl.create(s);
    }

    @Test(expected = ResourceNullNumber.class)
    public void createSector_Test_Fail() throws Exception
    {
        Sector s = new Sector(null, SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE,
                                    SectorConstants.INVALID_SECTOR_ROWS, 
                                    SectorConstants.INVALID_SECTOR_COLUMNS);
        s.setHall(new Hall(HallConstants.PERSISTED_HALL_ID));
        sectorServiceImpl.create(s);
    }

    
    @Test
    @Rollback
    @Transactional
    public void createSector_when_allValid_then_createSector() throws Exception{
        
        int numberOfSectors = sectorRepository.findAll().size();
        
        Sector s = new Sector(null, SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE,
                                    SectorConstants.PERSISTED_SECTOR_ROWS, 
                                    SectorConstants.PERSISTED_SECTOR_COLUMNS);
        s.setHall(new Hall(HallConstants.PERSISTED_HALL_ID));
        Sector savedSector = sectorServiceImpl.create(s);

        assertEquals(numberOfSectors + 1, sectorRepository.findAll().size());
        assertEquals(SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE, savedSector.getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateSector_when_ID_notValid_should_throw_ResourceNotFound() throws Exception
    {
        sectorServiceImpl.update(new Sector(SectorConstants.INVALID_SECTOR_ID));
    }

    @Test(expected = TicketBoughtOrReservedException.class)
    @Rollback
    @Transactional
    public void updateSector_Test_Fail() throws Exception
    {
        int numberOfSectors = sectorRepository.findAll().size();

        Sector s = new Sector(      SectorConstants.PERSISTED_SECTOR_ID,
                                    SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE,
                                    SectorConstants.PERSISTED_SECTOR_ROWS, 
                                    SectorConstants.PERSISTED_SECTOR_COLUMNS);
        s.setHall(new Hall(HallConstants.PERSISTED_HALL_ID));
        Sector sector = sectorServiceImpl.update(s);
        
        assertEquals(numberOfSectors, sectorRepository.findAll().size());
        assertEquals(sector.getName(), SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE);


    }
    
    @Test
    @Rollback
    @Transactional
    public void updateSector_Test_Success() throws Exception
    {

        Sector s = new Sector(		SectorConstants.PERSISTED_SECTOR_ID3,
                                    SectorConstants.VALID_SECTOR_NAME_FOR_PERSISTANCE,
                                    SectorConstants.PERSISTED_SECTOR_ROWS, 
                                    SectorConstants.PERSISTED_SECTOR_COLUMNS);
        s.setHall(new Hall(HallConstants.PERSISTED_HALL_ID));
        sectorServiceImpl.update(s);

    }
    
    @Test
    public void findAll_Sectors_By_HallID_should_returnSector() throws Exception
    {
        Collection<Sector> foundSector = sectorServiceImpl.getSectorsByHallId(SectorConstants.PERSISTED_SECTOR_HALL_ID);
        for(Sector s: foundSector) {
        	assertSame(s.getHall().getId(), SectorConstants.PERSISTED_SECTOR_HALL_ID);
        }
        
        assertEquals(foundSector.size(), 2);
    }
    

	@Test 
	public void findAllByHallId_Test_Fail() {
		Collection<Sector> result = sectorRepository.findAllByHallId(HallConstants.INVALID_HALL_ID);
		assertEquals(result.size(),0);

	}
	
	@Test 
	public void findAllByHallIdAndEventId_Test_Success() {
		
		Collection<Sector> result = sectorRepository.findAllByHallIdAndEventId(HallConstants.PERSISTED_HALL_ID, EventConstants.PERSISTED_EVENT_ID);
		assertSame(2, result.size());
		
		for(Sector s: result) {
			assertEquals(SectorConstants.PERSISTED_SECTOR_HALL_ID, s.getHall().getId());
		}
		
	}
	
	@Test 
	public void findAllByHallIdAndEventId_Test_Fail() {
		
		Collection<Sector> result = sectorRepository.findAllByHallIdAndEventId(HallConstants.INVALID_HALL_ID, EventConstants.INVALID_EVENT_ID);
		assertEquals(result.size(),0);
	}
    

}
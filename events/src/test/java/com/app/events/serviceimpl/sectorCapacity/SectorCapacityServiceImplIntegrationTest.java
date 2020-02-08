package com.app.events.serviceimpl.sectorCapacity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.transaction.Transactional;

import com.app.events.constants.HallConstants;
import com.app.events.constants.SectorCapacityConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.TicketBoughtOrReservedException;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.repository.SectorCapacityRepository;
import com.app.events.serviceimpl.SectorCapacityServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class SectorCapacityServiceImplIntegrationTest {

    @Autowired
    private SectorCapacityServiceImpl sectorCapacityServiceImpl;

    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;

    @Test
    public void findOneById_when_validID_then_return_SectorCapacity() throws ResourceNotFoundException
    {
        SectorCapacity sectorCap =  sectorCapacityServiceImpl.findOne(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID);

        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID, sectorCap.getId());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY, sectorCap.getCapacity());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE, sectorCap.getFree());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID, sectorCap.getSector().getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findOneById_when_InvalidID_thenThrow_ResourceNotFoundException() throws ResourceNotFoundException
    {
        sectorCapacityServiceImpl.findOne(SectorCapacityConstants.INVALID_SECTOR_CAPACITY_ID);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void create_when_validDataPassedIn_then_addNew() throws Exception
    {
        int sizeBeforeSave = sectorCapacityRepository.findAll().size();

        SectorCapacity sc = new SectorCapacity(
                null,
                new HashSet<>(),
                new Sector(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID),
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE);
        
        SectorCapacity savedSC = sectorCapacityServiceImpl.create(sc);

        int sizeAfterSave = sectorCapacityRepository.findAll().size();

        assertNotNull(savedSC);
        assertEquals(sizeAfterSave, sizeBeforeSave + 1);
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                    savedSC.getCapacity());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE,
                    savedSC.getFree());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID,
                    savedSC.getSector().getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Transactional
    @Rollback(true)
    public void create_when_invalidSectorID_then_throwResourceNotFound() throws Exception
    {
        int sizeBeforeSave = sectorCapacityRepository.findAll().size();

        SectorCapacity sc = new SectorCapacity(
                null,
                new HashSet<>(),
                new Sector(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_INVALID_ID),
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE);
        sectorCapacityServiceImpl.create(sc);

        int sizeAfterSave = sectorCapacityRepository.findAll().size();
        assertEquals(sizeAfterSave, sizeBeforeSave);
    }
    
    @Test
    public void findBySectorId_Test_Fail()
    {
        Collection<SectorCapacity> retVal = sectorCapacityServiceImpl.findSectorCapacityBySectorId(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_INVALID_ID);
        assertTrue(retVal.isEmpty());
    }
    
    @Test
    public void findBySectorId_Test_Success()
    {
        Collection<SectorCapacity> retVal = sectorCapacityServiceImpl.findSectorCapacityBySectorId(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID);
        assertFalse(retVal.isEmpty());
        Iterator<SectorCapacity> itr = retVal.iterator();
        while(itr.hasNext())
        {
            Long sectorID = itr.next().getSector().getId();
            assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID, sectorID);
        }
    }
    
    @Test(expected = ResourceNotFoundException.class)
    public void updateSectorCapacity_Test_Fail() throws Exception
    {
    	sectorCapacityServiceImpl.update(new SectorCapacity(SectorCapacityConstants.INVALID_SECTOR_CAPACITY_ID));
    }
    
    @Test
    @Rollback
    @Transactional
    public void updateSector_Test_Success() throws Exception
    {
        int numberOfSectors = sectorCapacityRepository.findAll().size();

        SectorCapacity s = new SectorCapacity(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID);
        SectorCapacity sectorCapacity = sectorCapacityServiceImpl.update(s);
        
        assertEquals(numberOfSectors, sectorCapacityRepository.findAll().size());


    }
  


}
package com.app.events.serviceimpl.sectorCapacity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Optional;

import com.app.events.constants.SectorCapacityConstants;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.repository.SectorCapacityRepository;
import com.app.events.serviceimpl.SectorCapacityServiceImpl;
import com.app.events.serviceimpl.SectorServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SectorCapacityServiceImplUnitTest {

    public static SectorCapacity persistedSectorCap;
    public static SectorCapacity sc;

    @Autowired
    public SectorCapacityServiceImpl sectorCapacityServiceImpl;

    @MockBean
    public SectorCapacityRepository sectorCapacityRepository;

    @MockBean
    public SectorServiceImpl sectorServiceImpl;

    @Before
    public void setUp() throws Exception{

        sc = new SectorCapacity(
                null,
                new HashSet<>(),
                new Sector(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID),
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE);


        persistedSectorCap = new SectorCapacity(
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID,
                new HashSet<>(),
                new Sector(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID),
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE);

        Mockito.when(sectorCapacityRepository.findById(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_ID)).
            thenReturn(Optional.of(persistedSectorCap));

        Mockito.when(sectorCapacityRepository.findById(SectorCapacityConstants.INVALID_SECTOR_CAPACITY_ID)).
            thenReturn(Optional.empty());

        Mockito.when(sectorServiceImpl.findOne(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID))
            .thenReturn(new Sector(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID));

        Mockito.when(sectorServiceImpl.findOne(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_INVALID_ID))
            .thenThrow(ResourceNotFoundException.class);

        Mockito.when(sectorCapacityRepository.save(persistedSectorCap)).thenReturn(persistedSectorCap);
        Mockito.when(sectorCapacityRepository.save(sc)).thenReturn(sc);

    }

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
    public void create_when_validDataPassedIn_then_addNew() throws Exception
    {
        
        SectorCapacity savedSC = sectorCapacityServiceImpl.create(sc);

        assertNotNull(savedSC);
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                    savedSC.getCapacity());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE,
                    savedSC.getFree());
        assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID,
                    savedSC.getSector().getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void create_when_invalidSectorID_then_throwResourceNotFound() throws Exception
    {
        SectorCapacity sc = new SectorCapacity(
                null,
                new HashSet<>(),
                new Sector(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_INVALID_ID),
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_CAPACITY,
                SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_FREE);
        sectorCapacityServiceImpl.create(sc);
    }

}
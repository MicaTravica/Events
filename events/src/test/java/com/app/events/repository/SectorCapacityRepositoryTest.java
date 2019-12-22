package com.app.events.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.Set;

import com.app.events.constants.SectorCapacityConstants;
import com.app.events.model.SectorCapacity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SectorCapacityRepositoryTest
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class SectorCapacityRepositoryTest {

    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;

    @Test
    public void findBySectorId_when_sectorID_invalid_returnEmptySet()
    {
        Set<SectorCapacity> retVal = sectorCapacityRepository.findBySectorId(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_INVALID_ID);
        assertTrue(retVal.isEmpty());
    }

    @Test
    public void findBySectorId_when_sectorID_valid_returnNotEmptySet()
    {
        Set<SectorCapacity> retVal = sectorCapacityRepository.findBySectorId(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID);
        assertFalse(retVal.isEmpty());
        Iterator<SectorCapacity> itr = retVal.iterator();
        while(itr.hasNext())
        {
            Long sectorID = itr.next().getSector().getId();
            assertEquals(SectorCapacityConstants.PERSISTED_SECTOR_CAPACITY_SECTOR_ID, sectorID);
        }
    }


}
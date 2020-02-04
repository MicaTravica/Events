package com.app.events.repository;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.SectorConstants;
import com.app.events.model.Sector;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class SectorRepositoryTest {
	
	@Autowired
	private SectorRepository sectorRepository;
	
	@Test 
	public void findAllByHallIdAndEventId_Test_Fail() {
		long idHall = 5;
		long idEvent = 5;
		
		Collection<Sector> result = sectorRepository.findAllByHallIdAndEventId(idHall, idEvent);
		assertEquals(result.size(),0);
	}
	
	@Test
	public void findAllByHallId_Test_Success() {
		long id = 1;
		
		Collection<Sector> result = sectorRepository.findAllByHallId(id);
		Sector sector = result.iterator().next();

		assertEquals(result.size(),2);
		assertEquals(sector.getId(), SectorConstants.PERSISTED_SECTOR_ID);
		assertEquals(sector.getHall().getId(), SectorConstants.PERSISTED_SECTOR_HALL_ID);
	}
	

	@Test 
	public void findAllByHallId_Test_Fail() {
		long id = 5;
		
		Collection<Sector> result = sectorRepository.findAllByHallId(id);
		assertEquals(result.size(),0);

	}

}

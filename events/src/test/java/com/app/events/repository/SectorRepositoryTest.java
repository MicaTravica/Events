package com.app.events.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

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
	public void findAllByHallIdAndEventId_Test_Success() {
		long idHall = 1l;
		long idEvent = 1l;
		
		Collection<Sector> result = sectorRepository.findAllByHallIdAndEventId(idHall, idEvent);
		assertSame(2, result.size());
		
		for(Sector s: result) {
			assertEquals(SectorConstants.PERSISTED_SECTOR_HALL_ID, s.getHall().getId());
		}
		
	}
	
	@Test
	public void findAllByHallId_Test_Success() {
		long id = 1;
		
		Collection<Sector> result = sectorRepository.findAllByHallId(id);
		assertEquals(2, result.size());
		
		for(Sector s: result) {
			assertEquals(SectorConstants.PERSISTED_SECTOR_HALL_ID, s.getHall().getId());
		}
	}
	

	@Test 
	public void findAllByHallId_Test_Fail() {
		long id = 5;
		
		Collection<Sector> result = sectorRepository.findAllByHallId(id);
		assertEquals(result.size(),0);

	}
}

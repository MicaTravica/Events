package com.app.events.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.HallConstants;
import com.app.events.model.Hall;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class HallRepositoryTest {
	@Autowired
	private HallRepository hallRepository;
	
	@Test
	public void findAllByEventsId_Test_Success() {
		long id = 1l;
		
		Optional<Hall> result = hallRepository.findById(id);
		Hall hall = result.get();
		assertEquals(hall.getId(), HallConstants.PERSISTED_HALL_ID);
	}
	
	@Test
	public void findAllByEventsId_Test_Fail() {
		long id = 5l;
		
		Optional<Hall> result = hallRepository.findById(id);
		assertSame(Optional.empty(), result);
	}
	
	
	@Test
	public void findAllByPlaceId_Test_Success() {
		long id = 1l;
		
		Collection<Hall> result = hallRepository.findAllByPlaceId(id);
		Hall hall = result.iterator().next();
						
		assertEquals(result.size(),2);
		assertEquals(hall.getId(), HallConstants.PERSISTED_HALL_ID);

	}
	
	@Test 
	public void findAllByPlaceId_Test_Fail() {
		long id = 5l;
		
		Collection<Hall> result = hallRepository.findAllByPlaceId(id);
		assertEquals(result.size(),0);

	}

}

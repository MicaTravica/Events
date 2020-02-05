package com.app.events.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;


import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.PlaceConstants;
import com.app.events.model.Place;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class PlaceRepositoryTest {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@Test
	public void findByCoordinated_Test() {
		double coor1 = 1;
		double coor2 = 1;
		
		Optional<Place> result = placeRepository.findByCoordinates(coor1, coor2);
		
		Place place = result.get();
		assertEquals(place.getId(), PlaceConstants.PERSISTED_PLACE_ID);
	}
	
	@Test
	public void findByCoordinated_Test_Fail() {
		double coor1 = 2;
		double coor2 = 2;
		
		Optional<Place> result = placeRepository.findByCoordinates(coor1, coor2);
		
		assertSame(Optional.empty(), result);

	
	}
	
	@Test
	public void findByAddress_Test_Fail() {
		String address = "Vuka Mandusica";
		
		Optional<Place> result = placeRepository.findByAddress(address);
		assertSame(Optional.empty(), result);

	}

	

}

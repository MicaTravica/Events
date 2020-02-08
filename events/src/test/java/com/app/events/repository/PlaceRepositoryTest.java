package com.app.events.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	public void findByAddress_Test_Fail() {
		String address = "Vuka Mandusica";
		
		Optional<Place> result = placeRepository.findByAddress(address);
		assertFalse(result.isPresent());
		assertSame(Optional.empty(), result);

	}
	
	@Test
	public void findByAddress_Test_Success() {
		String address = "Sime Milosevica";
		
		Optional<Place> result = placeRepository.findByAddress(address);
		assertTrue(result.isPresent());
		Place place = result.get();
		
		assertEquals(PlaceConstants.PERSISTED_PLACE_ID, place.getId());
		assertEquals(PlaceConstants.PERSISTED_PLACE_NAME, place.getName());
		assertEquals(PlaceConstants.PERSISTED_PLACE_ADDRESS, place.getAddress());
		
	}
	
	@Test
	public void search_noParams() {
		Page<Place> results = placeRepository.search(null, null, null);
		assertEquals(0, results.getSize());
		
	}
	
	@Test
	public void search_allParams() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Place> found = placeRepository.search("Hala1", "Sime Milosevica", pageable);
		for (Place place : found) {
			assertEquals(PlaceConstants.PERSISTED_PLACE_NAME, place.getName());
			assertEquals(PlaceConstants.PERSISTED_PLACE_ADDRESS, place.getAddress());
		;
		}
	}

	@Test
	public void search_oneParam() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Place> found = placeRepository.search("Hala1",null, pageable);
		for (Place place : found) {
			assertEquals(PlaceConstants.PERSISTED_PLACE_NAME, place.getName());
		}
	}
	
	@Test
	public void search_oneParam_sort() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
		Page<Place> found = placeRepository.search("Hala1",null, pageable);
		for (Place place : found) {
			assertEquals(PlaceConstants.PERSISTED_PLACE_NAME, place.getName());
		}
	}
	


}

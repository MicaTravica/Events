package com.app.events.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Media;
import com.app.events.service.MediaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class MediaServiceImplIntegrationTest {

	@Autowired
	private MediaService mediaService;

	@Test
	public void create_valid() throws Exception {
		Long eventId = new Long("1");
		Media media = new Media(null, "slika1", null);
		Media saved = mediaService.create(media, eventId);

		assertEquals(eventId, saved.getEvent().getId());
	}
	
	@Test
	public void create_throw() {
		Long eventId = new Long("3");
		Media media = new Media(null, "slika1", null);

		assertThrows(ResourceNotFoundException.class, () -> mediaService.create(media, eventId));
	}
	
	@Test
	public void createMedia_valid() throws ResourceNotFoundException {
		Long eventId = new Long("2");
		
		Media m1 = new Media(null, "slika2", null);
		Media m2 = new Media(null, "slika3", null);
		Collection<Media> mediaList = new ArrayList<Media>();
		mediaList.add(m1);
		mediaList.add(m2);

		Collection<Media> saved = mediaService.createMedias(mediaList, eventId);
		
		assertEquals(2, saved.size());
	}
	
	@Test
	public void createMedia_throw() {
		Long eventId = new Long("3");
		
		Media m1 = new Media(null, "slika2", null);
		Media m2 = new Media(null, "slika3", null);
		Collection<Media> mediaList = new ArrayList<Media>();
		mediaList.add(m1);
		mediaList.add(m2);

		assertThrows(ResourceNotFoundException.class, () -> mediaService.createMedias(mediaList, eventId));
	}
	
	@Test
	public void findOne_valid() throws ResourceNotFoundException {
		Long id = new Long("1");
		Media found = mediaService.findOne(id);
		
		assertEquals(id, found.getId());
	}
	
	@Test
	public void findOne_throw() {
		Long id = new Long("10");
		assertThrows(ResourceNotFoundException.class, () -> mediaService.findOne(id));
	}
	
	@Test void findAllForEvent() {
		Long id = new Long("2");
		Collection<Media> found = mediaService.findAllForEvent(id);
		
		assertEquals(2, found.size());
	}
	
}

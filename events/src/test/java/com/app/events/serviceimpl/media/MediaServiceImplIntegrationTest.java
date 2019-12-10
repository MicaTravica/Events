package com.app.events.serviceimpl.media;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.events.constants.EventConstants;
import com.app.events.constants.MediaConstants;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Media;
import com.app.events.service.MediaService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MediaServiceImplIntegrationTest {

	@Autowired
	private MediaService mediaService;


	@Test
    @Transactional
    @Rollback(true)
	public void create_valid() throws Exception {
		Media media = new Media(null, MediaConstants.NEW_MEDIA_PATH, null);
		Media created = mediaService.create(media, EventConstants.PERSISTED_EVENT_ID2);
		
		assertNotNull(created.getId());
		assertEquals(MediaConstants.NEW_MEDIA_PATH, created.getPath());
		assertEquals(EventConstants.PERSISTED_EVENT_ID2, created.getEvent().getId());
	}

	@Test
	public void create_throwResourceNotFoundException() {
		Media media = new Media(null, MediaConstants.NEW_MEDIA_PATH, null);
		
		assertThrows(ResourceNotFoundException.class, () -> mediaService.create(media, EventConstants.INVALID_EVENT_ID));
	}

	@Test
    @Transactional
    @Rollback(true)
	public void createMedias_valid() throws ResourceNotFoundException {

		Media media1 = new Media(null, MediaConstants.NEW_MEDIA_PATH, null);
		Media media2 = new Media(null, MediaConstants.NEW_MEDIA_PATH2, null);
		Collection<Media> mediaList = new ArrayList<>();
		mediaList.add(media1);
		mediaList.add(media2);
		Collection<Media> created = mediaService.createMedias(mediaList, EventConstants.PERSISTED_EVENT_ID2);
		
		assertEquals(mediaList.size(), created.size());
	}

	@Test
	public void createMedias_throwResourceNotFoundException() {
		Collection<Media> mediaList = new ArrayList<>();
		assertThrows(ResourceNotFoundException.class, () -> mediaService.createMedias(mediaList, EventConstants.INVALID_EVENT_ID));
	}

	@Test
	public void findOne_valid() throws ResourceNotFoundException {
		Media found = mediaService.findOne(MediaConstants.PERSISTED_MEDIA_ID);
		assertEquals(MediaConstants.PERSISTED_MEDIA_ID, found.getId());
		assertEquals(MediaConstants.PERSISTED_MEDIA_PATH, found.getPath());
	}

	@Test
	public void findOne_throwResourceNotFoundException() {
		assertThrows(ResourceNotFoundException.class, () -> mediaService.findOne(MediaConstants.INVALID_MEDIA_ID));
	}

	@Test
	public void findAllForEvent() {
		Collection<Media> mediaList = mediaService.findAllForEvent(EventConstants.PERSISTED_EVENT_ID);
		assertEquals(MediaConstants.SUM_OF_EVENTS_MEDIAS, mediaList.size());
	}
}

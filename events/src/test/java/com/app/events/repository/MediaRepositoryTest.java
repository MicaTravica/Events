package com.app.events.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.EventConstants;
import com.app.events.constants.MediaConstants;
import com.app.events.model.Media;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class MediaRepositoryTest {
	
	@Autowired
	private MediaRepository mediaRepository;
	
	@Test
	public void findAllByEventId_Test_Success() {
		
		Optional<Media> results = mediaRepository.findById(EventConstants.PERSISTED_EVENT_ID);
		assertTrue(results.isPresent());	
		Media media = results.get();
		assertEquals(MediaConstants.PERSISTED_MEDIA_ID, media.getId());
		assertEquals(MediaConstants.PERSISTED_MEDIA_PATH, media.getPath());
	}
	
	@Test
	public void findAllByEventId_Test_Fail() {
		
		Optional<Media> results = mediaRepository.findById(EventConstants.INVALID_EVENT_ID);
		assertFalse(results.isPresent());
	}
	

}

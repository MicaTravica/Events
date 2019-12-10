package com.app.events.serviceimpl.media;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.Media;
import com.app.events.repository.EventRepository;
import com.app.events.repository.MediaRepository;
import com.app.events.service.MediaService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class MediaServiceImplUnitTest {

	public static Event E1 = new Event(new Long(1));
	public static Event E2 = new Event(new Long(2));

	public static Media M1 = new Media(null, "slika1", E1);
	public static Media M2 = new Media(null, "slika2", E2);
	public static Media M3 = new Media(null, "slika3", E2);
	public static Media M4 = new Media(null, "slika4", E2);
	public static Media M5 = new Media(null, "slika5", null);
	public static Media M6 = new Media(new Long(6), "slika6", null);
	public static Media CM1 = new Media(new Long(1), "slika1", E1);
	public static Media CM2 = new Media(new Long(2), "slika2", E2);
	public static Media CM3 = new Media(new Long(3), "slika3", E2);
	public static Media CM4 = new Media(new Long(4), "slika4", E2);
	public static Media CM6 = new Media(new Long(6), "slika6", E2); 
	
	@Autowired
	private MediaService mediaService;

	@MockBean
	private MediaRepository mediaRepositoryMocked;
	
	@MockBean
	private EventRepository eventRepositoryMocked;

	@Before
	public void setUp() {
		Mockito.when(eventRepositoryMocked.findById(E1.getId())).thenReturn(Optional.of(E1));
		Mockito.when(eventRepositoryMocked.findById(E2.getId())).thenReturn(Optional.of(E2));
		
		Collection<Media> mediaList1 = new ArrayList<Media>();
		Collection<Media> mediaList2 = new ArrayList<Media>();
		mediaList1.add(CM2);
		mediaList1.add(CM3);
		mediaList1.add(CM4);
		Mockito.when(mediaRepositoryMocked.findById(CM1.getId())).thenReturn(Optional.of(CM1));
		Mockito.when(mediaRepositoryMocked.findById(CM6.getId())).thenReturn(Optional.of(CM6));
		Mockito.when(mediaRepositoryMocked.findById(new Long(10))).thenReturn(Optional.empty());
		Mockito.when(mediaRepositoryMocked.findAllByEventId(CM2.getEvent().getId())).thenReturn(mediaList1);
		Mockito.when(mediaRepositoryMocked.findAllByEventId(new Long(10))).thenReturn(mediaList2);
		Mockito.when(mediaRepositoryMocked.save(M1)).thenReturn(CM1);
		Mockito.when(mediaRepositoryMocked.save(M2)).thenReturn(CM2);
		Mockito.when(mediaRepositoryMocked.save(M3)).thenReturn(CM3);
		Mockito.when(mediaRepositoryMocked.save(M4)).thenReturn(CM4);
		Mockito.when(mediaRepositoryMocked.save(M6)).thenReturn(M6);
		Mockito.when(mediaRepositoryMocked.save(M5)).thenReturn(null);
	}

	@Test
	public void findOneById_Valid() throws ResourceNotFoundException {
		Long id = new Long(1);
		Media found = mediaService.findOne(id);
		
		assertEquals(id.longValue(), found.getId().longValue());
		
	}
	
	@Test
	public void findOneById_ThrowResourceNotFoundException() {
		Long id = new Long(10);
		assertThrows(ResourceNotFoundException.class, () -> mediaService.findOne(id));
	}
	
	@Test
	public void findAllForEvent_Found() {
		Collection<Media> found = mediaService.findAllForEvent(E2.getId());
		
		assertEquals(3, found.size());
	}
	
	@Test
	public void findAllForEvent_NotFound() {
		Long eventId = new Long(10);
		Collection<Media> found = mediaService.findAllForEvent(eventId);
		
		assertEquals(0, found.size());
	}
	
	@Test
	public void create_Valid() throws Exception {
		Media saved = mediaService.create(M1, E1.getId());

		assertNotNull(saved.getId());
		assertEquals(M1.getPath(), saved.getPath());
	}
	
	@Test
	public void createMedias_Valid() throws ResourceNotFoundException {
		Collection<Media> mediaList = new ArrayList<>();
		mediaList.add(M2);
		mediaList.add(M3);
		mediaList.add(M4);
		Collection<Media> saved = mediaService.createMedias(mediaList, E2.getId());
		
		verify(eventRepositoryMocked, atMostOnce()).findById(E2.getId());
		assertEquals(3, saved.size());
	}
	
	@Test
	public void delete_Valid() throws ResourceNotFoundException {
		Long id = new Long(1);
		mediaService.delete(id);
		verify(mediaRepositoryMocked, atMostOnce()).findById(id);
		verify(mediaRepositoryMocked, atMostOnce()).deleteById(id);
	}
	
}

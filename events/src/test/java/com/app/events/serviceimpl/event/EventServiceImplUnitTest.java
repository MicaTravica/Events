package com.app.events.serviceimpl.event;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.exception.BadEventStateException;
import com.app.events.exception.CollectionIsEmptyException;
import com.app.events.exception.DateException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.EventType;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.EventRepository;
import com.app.events.service.EventService;
import com.app.events.service.HallService;
import com.app.events.service.SectorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EventServiceImplUnitTest {

	public static Event EVENT_VALID;
	public static Long EVENT_INVALID_ID = -1L;

	public static Hall HALL_FOR_EVENT;
	public static Hall HALL_FOR_EVENT2;
	public static Hall HALL_FOR_EVENT3;
	public static Hall HALL_FOR_EVENT4;

	public static Sector SECTOR_FOR_EVENT;

	public static Event EVENT_CREATE;
	public static Event EVENT_UPDATE;
	public static Event EVENT_CREATE_UPDATE1;
	public static Event EVENT_CREATE_UPDATE2;
	public static Event EVENT_CREATE_UPDATE3;
	public static Event EVENT_CREATE_UPDATE4;
	public static Event EVENT_CREATE_UPDATE5;
	public static Event EVENT_CREATE_UPDATE6;
	public static Event EVENT_CREATE_UPDATE7;
//	public static Event EVENT_UPDATE6;

	public static Event EVENT_CREATED;

	@Autowired
	private EventService eventService;

	@MockBean
	private EventRepository eventRepositoryMocked;

	@MockBean
	private HallService hallService;

	@MockBean
	private SectorService sectorService;

	@Before
	public void setUp() throws ResourceNotFoundException {

		SECTOR_FOR_EVENT = new Sector(1L);

		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(SECTOR_FOR_EVENT);

		HALL_FOR_EVENT = new Hall(1L, "hala1");
		HALL_FOR_EVENT.setSectors(sectors);
		HALL_FOR_EVENT2 = new Hall(2L, "hala2");
		HALL_FOR_EVENT3 = new Hall(3L, "hala3");
		HALL_FOR_EVENT4 = new Hall(4L, "hala4");
		HALL_FOR_EVENT4.setSectors(new HashSet<>());

		HashSet<Hall> halls = new HashSet<>();
		halls.add(HALL_FOR_EVENT);
		HashSet<Hall> halls2 = new HashSet<>();
		halls2.add(HALL_FOR_EVENT2);
		HashSet<Hall> halls3 = new HashSet<>();
		halls3.add(HALL_FOR_EVENT3);
		HashSet<Hall> halls4 = new HashSet<>();
		halls4.add(HALL_FOR_EVENT4);

		EVENT_VALID = new Event(3L, "Dogadjaj", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls, new HashSet<>(), new HashSet<>());

		EVENT_CREATE = new Event(null, "Kreirani dogadjaj", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls, new HashSet<>(), new HashSet<>());
		EVENT_UPDATE = new Event(1L, "Izmenjeni dogadjaj", "Jako lep", new Date(), new Date(), EventState.FINISHED,
				EventType.SPORT, halls, new HashSet<>(), new HashSet<>());
		EVENT_CREATED = new Event(2L, "Kreirani dogadjaj", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls, new HashSet<>(), new HashSet<>());

		EVENT_CREATE_UPDATE1 = new Event(1L, "Dogadjaj2", "Jako lepo", null, null, EventState.AVAILABLE,
				EventType.SPORT, halls, new HashSet<>(), new HashSet<>());
		EVENT_CREATE_UPDATE2 = new Event(1L, "Dogadjaj2", "Jako lepo", new Date(), new Date(), EventState.FINISHED,
				EventType.SPORT, halls, new HashSet<>(), new HashSet<>());
		EVENT_CREATE_UPDATE3 = new Event(null, "Dogadjaj3", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, new HashSet<>(), new HashSet<>(), new HashSet<>());
		EVENT_CREATE_UPDATE4 = new Event(null, "Dogadjaj3", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls2, new HashSet<>(), new HashSet<>());
		EVENT_CREATE_UPDATE5 = new Event(1L, "Dogadjaj4", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls3, new HashSet<>(), new HashSet<>());
		EVENT_CREATE_UPDATE6 = new Event(1L, "Dogadjaj4", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls4, new HashSet<>(), new HashSet<>());
		EVENT_CREATE_UPDATE7 = new Event(1L, "Dogadjaj4", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls, new HashSet<>(), new HashSet<>());
//
//		EVENT_UPDATE6 = new Event(-1L, "Dogadjaj4", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
//				EventType.SPORT, halls3, new HashSet<>(), new HashSet<>());

		Mockito.when(eventRepositoryMocked.findById(EVENT_VALID.getId())).thenReturn(Optional.of(EVENT_VALID));
		Mockito.when(eventRepositoryMocked.findById(EVENT_UPDATE.getId())).thenReturn(Optional.of(EVENT_UPDATE));

		Mockito.when(sectorService.findOne(SECTOR_FOR_EVENT.getId())).thenReturn(SECTOR_FOR_EVENT);

		Mockito.when(hallService.findOne(HALL_FOR_EVENT.getId())).thenReturn(HALL_FOR_EVENT);
		Mockito.when(hallService.findOne(HALL_FOR_EVENT2.getId())).thenThrow(ResourceNotFoundException.class);
		Mockito.when(hallService.findOne(HALL_FOR_EVENT3.getId())).thenReturn(HALL_FOR_EVENT3);
		Mockito.when(hallService.findOne(HALL_FOR_EVENT4.getId())).thenReturn(HALL_FOR_EVENT4);

		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT.getId(), EVENT_CREATE.getFromDate(),
				EVENT_CREATE.getToDate())).thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT3.getId(), EVENT_CREATE_UPDATE5.getFromDate(),
				EVENT_CREATE_UPDATE5.getToDate())).thenReturn(true);
		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT4.getId(), EVENT_CREATE_UPDATE6.getFromDate(),
				EVENT_CREATE_UPDATE6.getToDate())).thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT.getId(), EVENT_CREATE_UPDATE7.getFromDate(),
				EVENT_CREATE_UPDATE7.getToDate())).thenReturn(false);

		Mockito.when(eventRepositoryMocked.hallHaveEventUpdate(HALL_FOR_EVENT.getId(), EVENT_UPDATE.getFromDate(),
				EVENT_UPDATE.getToDate(), EVENT_UPDATE.getId())).thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEventUpdate(HALL_FOR_EVENT3.getId(),
				EVENT_CREATE_UPDATE5.getFromDate(), EVENT_CREATE_UPDATE5.getToDate(), EVENT_CREATE_UPDATE5.getId()))
				.thenReturn(true);
		Mockito.when(eventRepositoryMocked.hallHaveEventUpdate(HALL_FOR_EVENT4.getId(),
				EVENT_CREATE_UPDATE6.getFromDate(), EVENT_CREATE_UPDATE6.getToDate(), EVENT_CREATE_UPDATE6.getId()))
				.thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEventUpdate(HALL_FOR_EVENT.getId(),
				EVENT_CREATE_UPDATE7.getFromDate(), EVENT_CREATE_UPDATE7.getToDate(), EVENT_CREATE_UPDATE7.getId()))
				.thenReturn(false);

		Mockito.when(eventRepositoryMocked.save(EVENT_CREATE)).thenReturn(EVENT_CREATED);
		Mockito.when(eventRepositoryMocked.save(EVENT_CREATE_UPDATE7)).thenThrow(ValidationException.class);
		Mockito.when(eventRepositoryMocked.save(EVENT_UPDATE)).thenReturn(EVENT_UPDATE);
	}

	@Test
	public void findOne_valid() throws ResourceNotFoundException {
		Event found = eventService.findOne(EVENT_VALID.getId());

		assertEquals(EVENT_VALID.getId(), found.getId());
		assertEquals(EVENT_VALID.getDescription(), found.getDescription());
		assertEquals(EVENT_VALID.getName(), found.getName());
		assertEquals(EVENT_VALID.getEventType(), found.getEventType());
	}

	@Test
	public void findOne_throwResourceNotFoundException() {
		assertThrows(ResourceNotFoundException.class, () -> eventService.findOne(EVENT_INVALID_ID));
	}

	@Test
	public void create_valid() throws Exception {
		Event created = eventService.create(EVENT_CREATE);

		assertNotNull(created.getId());
		assertEquals(EVENT_CREATED.getName(), created.getName());
		assertEquals(EVENT_CREATED.getDescription(), created.getDescription());
		assertEquals(EVENT_CREATED.getHalls().size(), created.getHalls().size());
		assertNotNull(created.getMediaList());
		assertNotNull(created.getPriceLists());
	}

	@Test
	public void create_throwsDateException_badDate() {
		assertThrows(DateException.class, () -> eventService.create(EVENT_CREATE_UPDATE1));
	}

	@Test
	public void create_throwsBadEventStateException() {
		assertThrows(BadEventStateException.class, () -> eventService.create(EVENT_CREATE_UPDATE2));
	}

	@Test
	public void create_throwsCollectionIsEmptyException_halls() {
		assertThrows(CollectionIsEmptyException.class, () -> eventService.create(EVENT_CREATE_UPDATE3));
	}

	@Test
	public void create_throwsResourceNotFoundException() {
		assertThrows(ResourceNotFoundException.class, () -> eventService.create(EVENT_CREATE_UPDATE4));
	}

	@Test
	public void create_throwsDateException_hallIsNotAvailable() {
		assertThrows(DateException.class, () -> eventService.create(EVENT_CREATE_UPDATE5));
	}

	@Test
	public void create_throwsCollectionIsEmptyException_sectors() {
		assertThrows(CollectionIsEmptyException.class, () -> eventService.create(EVENT_CREATE_UPDATE6));
	}
	
	@Test
	public void create_throwsValidationException() {
		assertThrows(ValidationException.class, () -> eventService.create(EVENT_CREATE_UPDATE7));
	}
//
//	@Test
//	public void update_valid() throws Exception {
//		Event updated = eventService.update(EVENT_UPDATE);
//
//		assertEquals(EVENT_UPDATE.getId(), updated.getId());
//		assertEquals(EVENT_UPDATE.getName(), updated.getName());
//		assertEquals(EVENT_UPDATE.getDescription(), updated.getDescription());
//		assertEquals(EVENT_UPDATE.getEventState(), updated.getEventState());
//		assertEquals(EVENT_UPDATE.getHalls().size(), updated.getHalls().size());
//	}
//	
//	@Test
//	public void update_throwsResourceNotFoundException_badEvent() {
//		assertThrows(ResourceNotFoundException.class, () -> eventService.update(EVENT_UPDATE5));
//	}
//	
//	@Test
//	public void update_throwsDateException_badDates() {
//		assertThrows(DateException.class, () -> eventService.update(EVENT_CREATE_UPDATE2));
//	}
//
//	@Test
//	public void update_throwsResourceNotFoundException_badHall() {
//		assertThrows(ResourceNotFoundException.class, () -> eventService.update(EVENT_CREATE_UPDATE3));
//	}
//
//	@Test
//	public void update_throwsDateException_hallIsNotAvailable() {
//		assertThrows(DateException.class, () -> eventService.update(EVENT_CREATE_UPDATE4));
//	}

}

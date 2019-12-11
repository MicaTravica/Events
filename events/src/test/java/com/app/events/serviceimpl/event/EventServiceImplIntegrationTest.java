package com.app.events.serviceimpl.event;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

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
import com.app.events.constants.HallConstans;
import com.app.events.exception.DateException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.service.EventService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class EventServiceImplIntegrationTest {

	@Autowired
	private EventService eventService;

	@Test
	public void findOne_valid() throws ResourceNotFoundException {
		Event found = eventService.findOne(EventConstants.PERSISTED_EVENT_ID);

		assertEquals(EventConstants.PERSISTED_EVENT_ID, found.getId());
	}

	@Test
	public void findOne_throwResourceNotFoundExceptino() {
		assertThrows(ResourceNotFoundException.class, () -> eventService.findOne(EventConstants.INVALID_EVENT_ID));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_valid() throws Exception {
		HashSet<Hall> halls = new HashSet<>();
		Hall h = new Hall(HallConstans.PERSISTED_HALL_ID);

		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(1L));
		h.setSectors(sectors);
		halls.add(h);
		
		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
		Event created = eventService.create(event);

		assertNotNull(created.getId());
		assertEquals(event.getName(), created.getName());
		assertEquals(event.getDescription(), created.getDescription());
		assertEquals(event.getFromDate(), created.getFromDate());
		assertEquals(event.getToDate(), created.getToDate());
		assertEquals(event.getEventState(), created.getEventState());
		assertEquals(event.getEventType(), created.getEventType());
		assertEquals(event.getHalls().size(), created.getHalls().size());
	}

//	@Test
//	@Transactional
//	@Rollback(true)
//	public void create_throwsDateException_badDates() {
//		HashSet<Hall> halls = new HashSet<>();
//		halls.add(new Hall(HallConstans.PERSISTED_HALL_ID));
//		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
//				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.EVENT_FROM_DATE_BAD, EventConstants.EVENT_TO_DATE_BAD, EventConstants.NEW_EVENT_EVENT_STATE,
//				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
//
//		assertThrows(DateException.class, () -> eventService.create(event));
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void create_throwsResourceNotFoundException() {
//		HashSet<Hall> halls = new HashSet<>();
//		halls.add(new Hall(HallConstans.INVALID_HALL_ID));
//		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
//				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
//				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
//				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
//
//		assertThrows(ResourceNotFoundException.class, () -> eventService.create(event));
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void create_throwsDateException_hallIsNotAvailable() {
//		HashSet<Hall> halls = new HashSet<>();
//		halls.add(new Hall(HallConstans.PERSISTED_HALL_ID));
//		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
//				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.PERSISTED_EVENT_FROM_DATE,
//				EventConstants.PERSISTED_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
//				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
//
//		assertThrows(DateException.class, () -> eventService.create(event));
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void update_valid() throws Exception {
//		HashSet<Hall> halls = new HashSet<>();
//		halls.add(new Hall(HallConstans.PERSISTED_HALL_ID));
//		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
//				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
//				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
//				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
//		Event updated = eventService.update(event);
//
//		assertNotNull(updated.getId());
//		assertEquals(event.getName(), updated.getName());
//		assertEquals(event.getDescription(), updated.getDescription());
//		assertEquals(event.getFromDate(), updated.getFromDate());
//		assertEquals(event.getToDate(), updated.getToDate());
//		assertEquals(event.getEventState(), updated.getEventState());
//		assertEquals(event.getEventType(), updated.getEventType());
//		assertEquals(event.getHalls().size(), updated.getHalls().size());
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void update_throwsResourceNotFoundExcpetion_badEvent() {
//		HashSet<Hall> halls = new HashSet<>();
//		halls.add(new Hall(HallConstans.PERSISTED_HALL_ID));
//		Event event = new Event(EventConstants.INVALID_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
//				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
//				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
//				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
//
//		assertThrows(ResourceNotFoundException.class, () -> eventService.update(event));
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void update_throwsDateException_badDates() {
//		HashSet<Hall> halls = new HashSet<>();
//		halls.add(new Hall(HallConstans.PERSISTED_HALL_ID));
//		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
//				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.EVENT_FROM_DATE_BAD,
//				EventConstants.EVENT_TO_DATE_BAD, EventConstants.UPDATE_EVENT_EVENT_STATE,
//				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
//
//		assertThrows(DateException.class, () -> eventService.update(event));
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void update_throwsResourceNotFoundExcpetion_badHall() {
//		HashSet<Hall> halls = new HashSet<>();
//		halls.add(new Hall(HallConstans.INVALID_HALL_ID));
//		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
//				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
//				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
//				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
//
//		assertThrows(ResourceNotFoundException.class, () -> eventService.update(event));
//	}
//
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void update_throwsDateException_hallIsNotAvailable() {
//		HashSet<Hall> halls = new HashSet<>();
//		halls.add(new Hall(HallConstans.PERSISTED_HALL_ID));
//		Event event = new Event(EventConstants.PERSISTED_EVENT_ID2, EventConstants.UPDATE_EVENT_NAME,
//				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.PERSISTED_EVENT_FROM_DATE,
//				EventConstants.PERSISTED_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
//				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
//
//		assertThrows(DateException.class, () -> eventService.update(event));
//	}
}

package com.app.events.serviceimpl.event;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.app.events.constants.EventConstants;
import com.app.events.constants.HallConstants;
import com.app.events.constants.SectorConstants;
import com.app.events.exception.BadEventStateException;
import com.app.events.exception.CollectionIsEmptyException;
import com.app.events.exception.DateException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.TicketIsBoughtException;
import com.app.events.model.Event;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.repository.EventRepository;
import com.app.events.service.EventService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventServiceImplIntegrationTest {

	@Autowired
	private EventService eventService;

	@Autowired
	private EventRepository eventRepository;

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
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.PERSISTED_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);

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
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsDateException_badDates() {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.PERSISTED_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.EVENT_FROM_DATE_BAD,
				EventConstants.EVENT_TO_DATE_BAD, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(DateException.class, () -> eventService.create(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsBadEventStateException() {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.PERSISTED_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.EVENT_EVENT_STATE_BAD,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(BadEventStateException.class, () -> eventService.create(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsCollectionIsEmptyException_halls() {
		HashSet<Hall> halls = new HashSet<>();
		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(CollectionIsEmptyException.class, () -> eventService.create(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsResourceNotFoundException_hall() {
		Hall hall = new Hall(HallConstants.INVALID_HALL_ID);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(ResourceNotFoundException.class, () -> eventService.create(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsDateException_hallIsNotAvailable() {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.PERSISTED_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.PERSISTED_EVENT_FROM_DATE,
				EventConstants.PERSISTED_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(DateException.class, () -> eventService.create(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsCollectionIsEmptyException_sectors() {
		HashSet<Sector> sectors = new HashSet<>();

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(CollectionIsEmptyException.class, () -> eventService.create(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void create_throwsResourceNotFoundException_sector() {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.INVALID_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.NEW_EVENT_ID, EventConstants.NEW_EVENT_NAME,
				EventConstants.NEW_EVENT_DESCRIPTION, EventConstants.NEW_EVENT_FROM_DATE,
				EventConstants.NEW_EVENT_TO_DATE, EventConstants.NEW_EVENT_EVENT_STATE,
				EventConstants.NEW_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(ResourceNotFoundException.class, () -> eventService.create(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_valid() throws Exception {
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());
		Event updated = eventService.update(event);

		assertEquals(event.getId(), updated.getId());
		assertEquals(event.getName(), updated.getName());
		assertEquals(event.getDescription(), updated.getDescription());
		assertEquals(event.getFromDate(), updated.getFromDate());
		assertEquals(event.getToDate(), updated.getToDate());
		assertEquals(event.getEventState(), updated.getEventState());
		assertEquals(event.getEventType(), updated.getEventType());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_throwsResourceNotFoundExcpetion_badEvent() {
		Event event = new Event(EventConstants.INVALID_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		assertThrows(ResourceNotFoundException.class, () -> eventService.update(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_throwsDateException_badDates() {
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.EVENT_FROM_DATE_BAD,
				EventConstants.EVENT_TO_DATE_BAD, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		assertThrows(DateException.class, () -> eventService.update(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_throwsBadEventStateException() {
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.EVENT_EVENT_STATE_BAD,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		assertThrows(BadEventStateException.class, () -> eventService.update(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void update_throwsDateException_hallIsNotAvailable() {
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID2, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.PERSISTED_EVENT_FROM_DATE,
				EventConstants.PERSISTED_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, new HashSet<>(), new HashSet<>(), new HashSet<>());

		assertThrows(DateException.class, () -> eventService.update(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_valid() throws Exception {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.PERSISTED_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);

		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());
		Event updated = eventService.updateHall(event);

		assertNotNull(updated.getId());
		assertEquals(event.getHalls().size(), updated.getHalls().size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_ResourceNotFoundException_event() {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.PERSISTED_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.INVALID_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(ResourceNotFoundException.class, () -> eventService.updateHall(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsTicketIsBoughtException() {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.PERSISTED_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID2, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(TicketIsBoughtException.class, () -> eventService.updateHall(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsCollectionIsEmptyException_halls() {
		HashSet<Hall> halls = new HashSet<>();
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(CollectionIsEmptyException.class, () -> eventService.updateHall(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsResourceNotFoundException_hall() {
		Hall hall = new Hall(HallConstants.INVALID_HALL_ID);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(ResourceNotFoundException.class, () -> eventService.updateHall(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsDateException_hallIsNotAvailable() {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.PERSISTED_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.PERSISTED_EVENT_FROM_DATE2,
				EventConstants.PERSISTED_EVENT_TO_DATE2, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(DateException.class, () -> eventService.updateHall(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsCollectionIsEmptyException_sectors() {
		HashSet<Sector> sectors = new HashSet<>();

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(CollectionIsEmptyException.class, () -> eventService.updateHall(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void updateHall_throwsResourceNotFoundException_sector() {
		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(new Sector(SectorConstants.INVALID_SECTOR_ID));

		Hall hall = new Hall(HallConstants.PERSISTED_HALL_ID);
		hall.setSectors(sectors);

		HashSet<Hall> halls = new HashSet<>();
		halls.add(hall);
		Event event = new Event(EventConstants.PERSISTED_EVENT_ID, EventConstants.UPDATE_EVENT_NAME,
				EventConstants.UPDATE_EVENT_DESCRIPTION, EventConstants.UPDATE_EVENT_FROM_DATE,
				EventConstants.UPDATE_EVENT_TO_DATE, EventConstants.UPDATE_EVENT_EVENT_STATE,
				EventConstants.UPDATE_EVENT_EVENT_TYPE, halls, new HashSet<>(), new HashSet<>());

		assertThrows(ResourceNotFoundException.class, () -> eventService.updateHall(event));
	}

	@Test
	@Transactional
	@Rollback(true)
	public void delete_valid() throws Exception {
		int countOfEvents = eventRepository.findAll().size();
		eventService.delete(EventConstants.PERSISTED_EVENT_ID3);
		int countOfEvnetsAfterDelete = eventRepository.findAll().size();

		assertEquals(countOfEvents - 1, countOfEvnetsAfterDelete);
	}
}

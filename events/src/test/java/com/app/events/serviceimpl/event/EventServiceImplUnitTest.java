package com.app.events.serviceimpl.event;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import javax.validation.ValidationException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import com.app.events.exception.SectorPriceListException;
import com.app.events.exception.TicketIsBoughtException;
import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.EventType;
import com.app.events.model.Hall;
import com.app.events.model.PriceList;
import com.app.events.model.Sector;
import com.app.events.repository.EventRepository;
import com.app.events.service.EventService;
import com.app.events.service.HallService;
import com.app.events.service.SectorService;
import com.app.events.service.TicketService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	public static Event EVENT_TO_UPDATE;
	public static Event EVENT_TO_UPDATE2;
	public static Event EVENT_UPDATE_SAVED;
	public static Event EVENT_CREATE_UPDATE1;
	public static Event EVENT_CREATE_UPDATE2;
	public static Event EVENT_CREATE_UPDATE3;
	public static Event EVENT_CREATE_UPDATE4;
	public static Event EVENT_CREATE_UPDATE5;
	public static Event EVENT_CREATE_UPDATE6;
	public static Event EVENT_CREATE_UPDATE7;
	public static Event EVENT_CREATE_UPDATE8;
	public static Event EVENT_UPDATE9;
	public static Event EVENT_UPDATE10;

	public static Event EVENT_CREATED;

	@Autowired
	private EventService eventService;

	@MockBean
	private EventRepository eventRepositoryMocked;

	@MockBean
	private HallService hallService;

	@MockBean
	private SectorService sectorService;

	@MockBean
	private TicketService ticketService;

	@Before
	public void setUp() throws ResourceNotFoundException {
		SECTOR_FOR_EVENT = new Sector(1L, "sektor", 2, 2, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

		HashSet<Sector> sectors = new HashSet<>();
		sectors.add(SECTOR_FOR_EVENT);

		HALL_FOR_EVENT = new Hall(1L, "hala1");
		HALL_FOR_EVENT.setSectors(sectors);
		SECTOR_FOR_EVENT.setHall(HALL_FOR_EVENT);
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

		HashSet<PriceList> priceList = new HashSet<>();
		priceList.add(new PriceList(null, 100, null, SECTOR_FOR_EVENT));

		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		date = c.getTime();
		EVENT_VALID = new Event(1L, "Dogadjaj", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls, priceList, new HashSet<>());

		EVENT_CREATE = new Event(null, "Kreirani dogadjaj", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls, priceList, new HashSet<>());
		EVENT_TO_UPDATE = new Event(3L, "dogadjaj", "Jako lepo", new Date(), new Date(), EventState.NOT_AVAILABLE,
				EventType.SPORT, halls, priceList, new HashSet<>());
		EVENT_TO_UPDATE2 = new Event(4L, "dogadjaj", "Jako lepo", new Date(), new Date(), EventState.NOT_AVAILABLE,
				EventType.SPORT, halls, priceList, new HashSet<>());
		EVENT_UPDATE = new Event(EVENT_TO_UPDATE.getId(), "Izmenjeni dogadjaj", "Jako lep", date, date,
				EventState.AVAILABLE, EventType.SPORT, halls, priceList, new HashSet<>());
		EVENT_UPDATE_SAVED = new Event(EVENT_TO_UPDATE.getId(), EVENT_UPDATE.getName(), EVENT_UPDATE.getDescription(),
				EVENT_UPDATE.getFromDate(), EVENT_UPDATE.getToDate(), EVENT_UPDATE.getEventState(),
				EVENT_UPDATE.getEventType(), EVENT_TO_UPDATE.getHalls(), EVENT_TO_UPDATE.getPriceLists(),
				EVENT_TO_UPDATE.getMediaList());
		EVENT_CREATED = new Event(2L, "Kreirani dogadjaj", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls, priceList, new HashSet<>());

		EVENT_CREATE_UPDATE1 = new Event(EVENT_TO_UPDATE.getId(), "Dogadjaj1", "Jako lepo", null, null,
				EventState.AVAILABLE, EventType.SPORT, halls, priceList, new HashSet<>());
		EVENT_CREATE_UPDATE2 = new Event(EVENT_TO_UPDATE.getId(), "Dogadjaj2", "Jako lepo", new Date(), new Date(),
				EventState.FINISHED, EventType.SPORT, halls, priceList, new HashSet<>());
		EVENT_CREATE_UPDATE3 = new Event(EVENT_TO_UPDATE.getId(), "Dogadjaj3", "Jako lepo", new Date(), new Date(),
				EventState.AVAILABLE, EventType.SPORT, new HashSet<>(), new HashSet<>(), new HashSet<>());
		EVENT_CREATE_UPDATE4 = new Event(EVENT_TO_UPDATE.getId(), "Dogadjaj4", "Jako lepo", new Date(), new Date(),
				EventState.AVAILABLE, EventType.SPORT, halls2, priceList, new HashSet<>());
		EVENT_CREATE_UPDATE5 = new Event(EVENT_TO_UPDATE2.getId(), "Dogadjaj5", "Jako lepo", date, date,
				EventState.AVAILABLE, EventType.SPORT, halls3, priceList, new HashSet<>());
		EVENT_CREATE_UPDATE6 = new Event(EVENT_TO_UPDATE.getId(), "Dogadjaj6", "Jako lepo", new Date(), new Date(),
				EventState.AVAILABLE, EventType.SPORT, halls4, priceList, new HashSet<>());
		EVENT_CREATE_UPDATE7 = new Event(EVENT_TO_UPDATE.getId(), "Dogadjaj7", "Jako lepo", new Date(), new Date(),
				EventState.AVAILABLE, EventType.SPORT, halls, priceList, new HashSet<>());
		EVENT_CREATE_UPDATE8 = new Event(EVENT_TO_UPDATE.getId(), "Dogadjaj8", "Jako lepo", new Date(), new Date(),
				EventState.AVAILABLE, EventType.SPORT, halls, new HashSet<>(), new HashSet<>());
		EVENT_UPDATE9 = new Event(-1L, "Dogadjaj9", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls3, priceList, new HashSet<>());
		EVENT_UPDATE10 = new Event(5L, "Dogadjaj10", "Jako lepo", new Date(), new Date(), EventState.AVAILABLE,
				EventType.SPORT, halls3, priceList, new HashSet<>());

		Mockito.when(eventRepositoryMocked.findById(EVENT_VALID.getId())).thenReturn(Optional.of(EVENT_VALID));
		Mockito.when(eventRepositoryMocked.findById(EVENT_UPDATE.getId())).thenReturn(Optional.of(EVENT_TO_UPDATE));
		Mockito.when(eventRepositoryMocked.findById(EVENT_CREATE_UPDATE5.getId()))
				.thenReturn(Optional.of(EVENT_TO_UPDATE2));
		Mockito.when(eventRepositoryMocked.findById(EVENT_UPDATE10.getId())).thenReturn(Optional.of(EVENT_UPDATE10));

		Mockito.when(ticketService.ticketForEventIsSale(EVENT_UPDATE10.getId())).thenReturn(true);

		Mockito.when(sectorService.findOne(SECTOR_FOR_EVENT.getId())).thenReturn(SECTOR_FOR_EVENT);

		Mockito.when(hallService.findOne(HALL_FOR_EVENT.getId())).thenReturn(HALL_FOR_EVENT);
		Mockito.when(hallService.findOne(HALL_FOR_EVENT2.getId())).thenThrow(ResourceNotFoundException.class);
		Mockito.when(hallService.findOne(HALL_FOR_EVENT3.getId())).thenReturn(HALL_FOR_EVENT3);
		Mockito.when(hallService.findOne(HALL_FOR_EVENT4.getId())).thenReturn(HALL_FOR_EVENT4);

		Mockito.when(hallService.findHallByEventId(EVENT_UPDATE.getId())).thenReturn(halls);
		Mockito.when(hallService.findHallByEventId(EVENT_CREATE_UPDATE5.getId())).thenReturn(halls3);

		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT.getId(), EVENT_CREATE.getFromDate(),
				EVENT_CREATE.getToDate())).thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT3.getId(), EVENT_CREATE_UPDATE5.getFromDate(),
				EVENT_CREATE_UPDATE5.getToDate())).thenReturn(true);
		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT4.getId(), EVENT_CREATE_UPDATE6.getFromDate(),
				EVENT_CREATE_UPDATE6.getToDate())).thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT.getId(), EVENT_CREATE_UPDATE7.getFromDate(),
				EVENT_CREATE_UPDATE7.getToDate())).thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEvent(HALL_FOR_EVENT.getId(), EVENT_CREATE_UPDATE8.getFromDate(),
				EVENT_CREATE_UPDATE8.getToDate())).thenReturn(false);

		Mockito.when(eventRepositoryMocked.hallHaveEventUpdate(HALL_FOR_EVENT.getId(), EVENT_UPDATE.getFromDate(),
				EVENT_UPDATE.getToDate(), EVENT_UPDATE.getId())).thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEventUpdate(HALL_FOR_EVENT.getId(),
				EVENT_CREATE_UPDATE8.getFromDate(), EVENT_CREATE_UPDATE8.getToDate(), EVENT_CREATE_UPDATE8.getId()))
				.thenReturn(false);
		Mockito.when(eventRepositoryMocked.hallHaveEventUpdate(HALL_FOR_EVENT3.getId(),
				EVENT_CREATE_UPDATE5.getFromDate(), EVENT_CREATE_UPDATE5.getToDate(), EVENT_CREATE_UPDATE5.getId()))
				.thenReturn(true);
		Mockito.when(eventRepositoryMocked.hallHaveEventUpdate(HALL_FOR_EVENT3.getId(), EVENT_TO_UPDATE2.getFromDate(),
				EVENT_TO_UPDATE2.getToDate(), EVENT_TO_UPDATE2.getId())).thenReturn(true);

		Mockito.when(eventRepositoryMocked.save(EVENT_CREATE)).thenReturn(EVENT_CREATED);
		Mockito.when(eventRepositoryMocked.save(EVENT_CREATE_UPDATE7)).thenThrow(ValidationException.class);
		Mockito.when(eventRepositoryMocked.save(EVENT_TO_UPDATE)).thenReturn(EVENT_UPDATE_SAVED);
	}

	@Test
	public void findOne_valid() throws ResourceNotFoundException {
		Event found = eventService.findOne(EVENT_VALID.getId());

		assertEquals(EVENT_VALID.getId(), found.getId());
		assertEquals(EVENT_VALID.getName(), found.getName());
		assertEquals(EVENT_VALID.getDescription(), found.getDescription());
		assertEquals(EVENT_VALID.getEventState(), found.getEventState());
		assertEquals(EVENT_VALID.getEventType(), found.getEventType());
		assertEquals(EVENT_VALID.getFromDate(), found.getFromDate());
		assertEquals(EVENT_VALID.getToDate(), found.getToDate());
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

	@Test
	public void create_throwsSectorPriceListException() {
		assertThrows(SectorPriceListException.class, () -> eventService.create(EVENT_CREATE_UPDATE8));
	}

	@Test
	public void update_valid() throws Exception {
		Event updated = eventService.update(EVENT_UPDATE);

		assertEquals(EVENT_UPDATE.getId(), updated.getId());
		assertEquals(EVENT_UPDATE.getName(), updated.getName());
		assertEquals(EVENT_UPDATE.getDescription(), updated.getDescription());
		assertEquals(EVENT_UPDATE.getEventState(), updated.getEventState());
		assertEquals(EVENT_UPDATE.getEventType(), updated.getEventType());
		assertEquals(EVENT_UPDATE.getFromDate(), updated.getFromDate());
		assertEquals(EVENT_UPDATE.getToDate(), updated.getToDate());
	}

	@Test
	public void update_throwsResourceNotFoundException_badEvent() {
		assertThrows(ResourceNotFoundException.class, () -> eventService.update(EVENT_UPDATE9));
	}

	@Test
	public void update_throwsDateException_badDates() {
		assertThrows(DateException.class, () -> eventService.update(EVENT_CREATE_UPDATE1));
	}

	@Test
	public void update_throwsBadEventStateException() {
		assertThrows(BadEventStateException.class, () -> eventService.update(EVENT_CREATE_UPDATE2));
	}

	@Test
	public void update_throwsDateException_hallIsNotAvailable() {
		assertThrows(DateException.class, () -> eventService.update(EVENT_CREATE_UPDATE5));
	}

	@Test
	public void updateHall_valid() throws Exception {
		Event updated = eventService.updateHall(EVENT_UPDATE);

		assertEquals(EVENT_UPDATE.getId(), updated.getId());
		assertEquals(EVENT_UPDATE.getHalls().size(), updated.getHalls().size());
	}

	@Test
	public void updateHall_throwsResourceNotFoundException_badEvent() {
		assertThrows(ResourceNotFoundException.class, () -> eventService.updateHall(EVENT_UPDATE9));
	}

	@Test
	public void updateHall_throwsTicketIsBoughtException() {
		assertThrows(TicketIsBoughtException.class, () -> eventService.updateHall(EVENT_UPDATE10));
	}

	@Test
	public void updateHall_throwsCollectionIsEmptyException_halls() {
		assertThrows(CollectionIsEmptyException.class, () -> eventService.updateHall(EVENT_CREATE_UPDATE3));
	}

	@Test
	public void updateHall_throwsResourceNotFoundException_badHall() {
		assertThrows(ResourceNotFoundException.class, () -> eventService.updateHall(EVENT_CREATE_UPDATE4));
	}

	@Test
	public void updateHall_throwsDateException_hallIsNotAvailable() {
		assertThrows(DateException.class, () -> eventService.updateHall(EVENT_CREATE_UPDATE5));
	}

	@Test
	public void updateHall_throwsCollectionIsEmptyException_sectors() {
		assertThrows(CollectionIsEmptyException.class, () -> eventService.updateHall(EVENT_CREATE_UPDATE6));
	}

	@Test
	public void updateHall_throwsSectorPriceListException() {
		assertThrows(SectorPriceListException.class, () -> eventService.updateHall(EVENT_CREATE_UPDATE8));
	}

	@Test
	public void delete_valid() throws ResourceNotFoundException {
		eventService.delete(EVENT_VALID.getId());

		verify(eventRepositoryMocked, atMostOnce()).deleteById(EVENT_VALID.getId());
	}

	@Test
	public void delete_throwsResourceNotFoundException() {
		assertThrows(ResourceNotFoundException.class, () -> eventService.delete(EVENT_INVALID_ID));
	}
}

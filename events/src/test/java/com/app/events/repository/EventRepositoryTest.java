package com.app.events.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

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

import com.app.events.constants.EventConstants;
import com.app.events.constants.HallConstants;
import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.EventType;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class EventRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	@Test
	public void hallHaveEvent_no() {
		Date fromDate = new GregorianCalendar(2020, Calendar.JANUARY, 3).getTime();
		Date toDate = new GregorianCalendar(2020, Calendar.JUNE, 22).getTime();
		Boolean result = eventRepository.hallHaveEvent(HallConstants.PERSISTED_HALL_ID, fromDate, toDate);
		assertFalse(result);
	}

	@Test
	public void hallHaveEvent_yesFromDate() {
		Date fromDate = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
		Date toDate = new GregorianCalendar(2020, Calendar.JUNE, 2).getTime();
		Boolean result = eventRepository.hallHaveEvent(HallConstants.PERSISTED_HALL_ID, fromDate, toDate);
		assertTrue(result);
	}

	@Test
	public void hallHaveEvent_yesToDate() {
		Date fromDate = new GregorianCalendar(2019, Calendar.DECEMBER, 31).getTime();
		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
		Boolean result = eventRepository.hallHaveEvent(HallConstants.PERSISTED_HALL_ID, fromDate, toDate);
		assertTrue(result);
	}

	@Test
	public void hallHaveEventUpdate_no() {
		Date fromDate = new GregorianCalendar(2019, Calendar.DECEMBER, 31).getTime();
		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
		Boolean result = eventRepository.hallHaveEventUpdate(HallConstants.PERSISTED_HALL_ID, fromDate, toDate,
				EventConstants.PERSISTED_EVENT_ID);
		assertFalse(result);
	}

	@Test
	public void hallHaveEventUpdate_yesFromDate() {
		Date fromDate = new GregorianCalendar(2019, Calendar.JANUARY, 2).getTime();
		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 4).getTime();
		Boolean result = eventRepository.hallHaveEventUpdate(HallConstants.PERSISTED_HALL_ID, fromDate, toDate,
				EventConstants.PERSISTED_EVENT_ID2);
		assertTrue(result);
	}

	@Test
	public void hallHaveEventUpdate_yesToDate() {
		Date fromDate = new GregorianCalendar(2019, Calendar.DECEMBER, 31).getTime();
		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
		Boolean result = eventRepository.hallHaveEventUpdate(HallConstants.PERSISTED_HALL_ID, fromDate, toDate,
				EventConstants.PERSISTED_EVENT_ID2);
		assertTrue(result);
	}

	@Test
	public void search_noParams() {
		Page<Event> found = eventRepository.search(null, null, null, null, null, null, null);
		assertEquals(0, found.getTotalElements());
	}

	@Test
	public void search_oneParam() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Event> found = eventRepository.search("", null, null, null, null, null, pageable);
		int count = eventRepository.findAll().size();
		assertEquals(count, found.getTotalElements());
	}

	@Test
	public void search_oneParam_Sort() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());
		Page<Event> found = eventRepository.search("", null, null, null, null, null, pageable);
		int count = eventRepository.findAll().size();
		assertEquals(count, found.getTotalElements());
		assertEquals("name: ASC", found.getSort().toString());
	}

	@Test
	public void search_dates() {
		Date fromDate = new GregorianCalendar(2019, Calendar.DECEMBER, 30).getTime();
		Date toDate = new GregorianCalendar(2021, Calendar.JANUARY, 20).getTime();
		Pageable pageable = PageRequest.of(0, 10);
		Page<Event> found = eventRepository.search("", fromDate, toDate, null, null, null, pageable);
		for (Event event : found) {
			assertTrue(event.getFromDate().after(fromDate));
			assertTrue(event.getToDate().before(toDate));
		}
	}

	@Test
	public void search_eventStateAndEventType() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Event> found = eventRepository.search("", null, null, EventState.AVAILABLE, EventType.SPORT, null,
				pageable);
		for (Event event : found) {
			assertEquals(EventState.AVAILABLE, event.getEventState());
			assertEquals(EventType.SPORT, event.getEventType());
		}
	}

	@Test
	public void search_allParams() {
		Date fromDate = new GregorianCalendar(2019, Calendar.NOVEMBER, 1).getTime();
		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 3).getTime();
		Pageable pageable = PageRequest.of(0, 10);
		Page<Event> found = eventRepository.search("UT", fromDate, toDate, EventState.AVAILABLE, EventType.SPORT, 1L,
				pageable);
		for (Event event : found) {
			assertTrue(event.getFromDate().after(fromDate));
			assertTrue(event.getToDate().before(toDate));
			assertEquals(EventState.AVAILABLE, event.getEventState());
			assertEquals(EventType.SPORT, event.getEventType());
		}
	}
	
	@Test
	public void findAllNotFinished() {
		Collection<Event> events = eventRepository.findAllNotFinished();
		
		assertEquals(4, events.size());
	}

	@Test
	public void findAllByPlaceId_Test_Fail() {
		Long placeID = 5l;
		Collection<Event> events = eventRepository.findAllByPlaceId(placeID);
		assertSame(0, events.size());
	}

	@Test
	public void findAllByPlaceId_Test_Success() {
		Long placeID = 1l;
		Collection<Event> events = eventRepository.findAllByPlaceId(placeID);
		assertSame(1, events.size());
		Event event = events.iterator().next();
		assertEquals(EventState.FINISHED, event.getEventState());
	}

	
	
}

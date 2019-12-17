package com.app.events.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.EventConstants;
import com.app.events.constants.HallConstants;
import com.app.events.model.Event;

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
	public void sarch_noParams() {
		Page<Event> found = eventRepository.search(null, null, null, null, null, null, null);
		assertEquals(0, found.getTotalElements());
	}

	@Test
	public void sarch_oneParam() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Event> found = eventRepository.search("", null, null, null, null, null, pageable);
		int count = eventRepository.findAll().size();
		for (Event event : found) {
			System.out.println(event.getId());
		}
		System.out.println(found.getNumber());// broj strnice
		System.out.println(found.getNumberOfElements()); // pronadjeno elemenata
		System.out.println(found.getSize()); // broj elemenata na stranici
		System.out.println(found.getTotalElements()); // ukupno elemenata
		System.out.println(found.getTotalPages()); // ukupno stranica
		System.out.println(found.get()); // ne znam
		System.out.println(found.getContent()); // tip Event
		System.out.println(found.getPageable());// sta sam poslala 0,10,unsorted
		System.out.println(found.getSort());
		found = eventRepository.search2("", pageable);
		assertEquals(count, found.getTotalElements());
		
	}
//
//	@Test
//	public void sarch_noParams() {
//		// testirati
//		Date fromDate = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
//		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
//		Pageable p = PageRequest.of(0, 1, Sort.by("name").ascending()); // za sortiranje heheheh
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Event> found = eventRepository.search(null, null, null, null, null, null, null);
//		System.out.println(found.getNumber());// broj strnice
//		System.out.println(found.getNumberOfElements()); // pronadjeno elemenata
//		System.out.println(found.getSize()); // broj elemenata na stranici
//		System.out.println(found.getTotalElements()); // ukupno elemenata
//		System.out.println(found.getTotalPages()); // ukupno stranica
//		System.out.println(found.get()); // ne znam
//		System.out.println(found.getContent()); // tip Event
//		System.out.println(found.getPageable());// sta sam poslala 0,10,unsorted
//		System.out.println(found.getSort()); // vrsta sorta
//		for (Event event : found) {
//			System.out.println(event.getId());
//		}
//
//	}
//
//	@Test
//	public void sarch_noParams() {
//		// testirati
//		Date fromDate = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
//		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
//		Pageable p = PageRequest.of(0, 1, Sort.by("name").ascending()); // za sortiranje heheheh
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Event> found = eventRepository.search(null, null, null, null, null, null, null);
//		System.out.println(found.getNumber());// broj strnice
//		System.out.println(found.getNumberOfElements()); // pronadjeno elemenata
//		System.out.println(found.getSize()); // broj elemenata na stranici
//		System.out.println(found.getTotalElements()); // ukupno elemenata
//		System.out.println(found.getTotalPages()); // ukupno stranica
//		System.out.println(found.get()); // ne znam
//		System.out.println(found.getContent()); // tip Event
//		System.out.println(found.getPageable());// sta sam poslala 0,10,unsorted
//		System.out.println(found.getSort()); // vrsta sorta
//		for (Event event : found) {
//			System.out.println(event.getId());
//		}
//
//	}
//
//	@Test
//	public void sarch_noParams() {
//		// testirati
//		Date fromDate = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
//		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
//		Pageable p = PageRequest.of(0, 1, Sort.by("name").ascending()); // za sortiranje heheheh
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Event> found = eventRepository.search(null, null, null, null, null, null, null);
//		System.out.println(found.getNumber());// broj strnice
//		System.out.println(found.getNumberOfElements()); // pronadjeno elemenata
//		System.out.println(found.getSize()); // broj elemenata na stranici
//		System.out.println(found.getTotalElements()); // ukupno elemenata
//		System.out.println(found.getTotalPages()); // ukupno stranica
//		System.out.println(found.get()); // ne znam
//		System.out.println(found.getContent()); // tip Event
//		System.out.println(found.getPageable());// sta sam poslala 0,10,unsorted
//		System.out.println(found.getSort()); // vrsta sorta
//		for (Event event : found) {
//			System.out.println(event.getId());
//		}
//
//	}
//
//	@Test
//	public void sarch_noParams() {
//		// testirati
//		Date fromDate = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
//		Date toDate = new GregorianCalendar(2020, Calendar.JANUARY, 2).getTime();
//		Pageable p = PageRequest.of(0, 1, Sort.by("name").ascending()); // za sortiranje heheheh
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Event> found = eventRepository.search(null, null, null, null, null, null, null);
//		System.out.println(found.getNumber());// broj strnice
//		System.out.println(found.getNumberOfElements()); // pronadjeno elemenata
//		System.out.println(found.getSize()); // broj elemenata na stranici
//		System.out.println(found.getTotalElements()); // ukupno elemenata
//		System.out.println(found.getTotalPages()); // ukupno stranica
//		System.out.println(found.get()); // ne znam
//		System.out.println(found.getContent()); // tip Event
//		System.out.println(found.getPageable());// sta sam poslala 0,10,unsorted
//		System.out.println(found.getSort()); // vrsta sorta
//		for (Event event : found) {
//			System.out.println(event.getId());
//		}
//
//	}
}

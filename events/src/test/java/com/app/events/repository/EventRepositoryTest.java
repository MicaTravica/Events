package com.app.events.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.EventConstants;
import com.app.events.constants.HallConstants;

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
}

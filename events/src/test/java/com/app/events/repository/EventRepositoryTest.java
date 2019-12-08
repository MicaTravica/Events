package com.app.events.repository;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.HallConstans;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@TestPropertySource("classpath:application-test.properties")
public class EventRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	@Test
	public void hallHaveEvent() {
		Date fromDate = new GregorianCalendar(2020, Calendar.JUNE, 21).getTime();
		Date toDate = new GregorianCalendar(2020, Calendar.JUNE, 22).getTime();
		Boolean result = eventRepository.hallHaveEvent(HallConstans.PERSISTED_HALL_ID, fromDate, toDate);
		assertTrue(result);
	}
}

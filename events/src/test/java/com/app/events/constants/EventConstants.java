package com.app.events.constants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.app.events.model.EventState;
import com.app.events.model.EventType;

/**
 * EventConstans
 */
public class EventConstants {

	public static final String URL_PREFIX = "/api/event";

	public static final Long PERSISTED_EVENT_ID = 1l;
	public static final String PERSISTED_EVENT_NAME = "UTAKMICA";
	public static final String PERSISTED_EVENT_DESCRIPTION = "dfa";
	public static final EventState PERSISTED_EVENT_EVENT_STATE = EventState.AVAILABLE;
	public static final EventType PERSISTED_EVENT_EVENT_TYPE = EventType.SPORT;
	public static final Date PERSISTED_EVENT_FROM_DATE = new GregorianCalendar(2020, Calendar.JANUARY, 1, 1, 0).getTime();
	public static final Date PERSISTED_EVENT_TO_DATE = new GregorianCalendar(2020, Calendar.JANUARY, 2, 1, 0).getTime();

	public static final Long PERSISTED_EVENT_ID2 = 2l;
	public static final Date PERSISTED_EVENT_FROM_DATE2 = new GregorianCalendar(2021, Calendar.JANUARY, 1, 1, 0).getTime();
	public static final Date PERSISTED_EVENT_TO_DATE2 = new GregorianCalendar(2021, Calendar.JANUARY, 2, 1, 0).getTime();
	public static final Long PERSISTED_EVENT_ID3 = 3l;

	public static final Long NEW_EVENT_ID = null;
	public static final String NEW_EVENT_NAME = "Dogadjaj";
	public static final String NEW_EVENT_DESCRIPTION = "lep opis";
	public static final EventState NEW_EVENT_EVENT_STATE = EventState.AVAILABLE;
	public static final EventType NEW_EVENT_EVENT_TYPE = EventType.CONCERT;
	public static final Date NEW_EVENT_FROM_DATE = new GregorianCalendar(2020, Calendar.JUNE, 1).getTime();
	public static final Date NEW_EVENT_TO_DATE = new GregorianCalendar(2020, Calendar.JUNE, 2).getTime();
	
	public static final String UPDATE_EVENT_NAME = "Dogadjaj izmenjen";
	public static final String UPDATE_EVENT_DESCRIPTION = "izmenjen lep opis";
	public static final EventState UPDATE_EVENT_EVENT_STATE = EventState.AVAILABLE;
	public static final EventType UPDATE_EVENT_EVENT_TYPE = EventType.DRAMA;
	public static final Date UPDATE_EVENT_FROM_DATE = new GregorianCalendar(2020, Calendar.JUNE, 1).getTime();
	public static final Date UPDATE_EVENT_TO_DATE = new GregorianCalendar(2020, Calendar.JUNE, 2).getTime();
	
	public static final Date EVENT_FROM_DATE_BAD = new GregorianCalendar(2020, Calendar.JUNE, 15).getTime();
	public static final Date EVENT_TO_DATE_BAD = new GregorianCalendar(2020, Calendar.JUNE, 12).getTime();
	public static final EventState EVENT_EVENT_STATE_BAD = EventState.FINISHED;
	
	public static final Long INVALID_EVENT_ID = -1l;
}

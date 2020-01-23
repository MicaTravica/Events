package com.app.events.serviceimpl;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.DateException;
import com.app.events.model.Event;
import com.app.events.service.EventService;
import com.app.events.service.ReportService;
import com.app.events.service.TicketService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private EventService eventService;

	@Autowired
	private TicketService ticketService;

	@Override
	public Map<String, Double> profitByEvent(Long placeId) {
		LinkedHashMap<String, Double> result = new LinkedHashMap<>();
		Collection<Event> events = eventService.findAllByPlaceId(placeId);
		for (Event event : events) {
			Double value = ticketService.findProfitByEventId(event.getId());
			result.put(event.getName(), value);
		}
		return result;
	}

	// o posecenosti, naziv dogadjaja, i broj prodatih karata
	@Override
	public Map<String, Double> attendanceByEvent(Long placeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Double> profitByTime(Long placeId, Date fromDate, Date toDate) throws DateException {
		if (!fromDate.before(toDate)) {
			throw new DateException("From date must be before to date!");
		} else if (!toDate.before(new Date())) {
			throw new DateException("To date must be before today!");
		}
		DateTime startDate = new DateTime(fromDate);
		DateTime endDate = new DateTime(toDate);
		int days = Days.daysBetween(startDate, endDate).getDays();
		int hours = Hours.hoursBetween(startDate, endDate).getHours();
		if (days < 11) {
			hours = 24;
		} else {
			hours = hours / 10;
			days = 10;
		}
		LinkedHashMap<String, Double> result = new LinkedHashMap<>();
		for (int i = 0; i < days; i++) {
			if (i == days - 1) {
				endDate = new DateTime(toDate);
			} else {
				endDate = startDate.plusHours(hours);
			}
			Double value = ticketService.findProfitByTime(placeId, startDate.toDate(), endDate.toDate());
			if (value == null) {
				value = 0.0;
			}
			String key = startDate.getDayOfMonth() + "/" + startDate.getMonthOfYear() + "/" + startDate.getYear() + "\n"
					+ endDate.getDayOfMonth() + "/" + endDate.getMonthOfYear() + "/" + endDate.getYear();
			result.put(key, value);
			startDate = endDate;
		}
		return result;
	}

	// o posecenosti za vremenski period i brojd poredatih karata
	@Override
	public Map<String, Double> attendanceByTime(Long placeId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

}

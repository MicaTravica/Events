package com.app.events.service;

import java.util.Date;
import java.util.Map;

import com.app.events.exception.DateException;

public interface ReportService {

	Map<String, Double> profitByEvent(Long placeId);

	Map<String, Double> attendanceByEvent(Long placeId);

	Map<String, Double> profitByTime(Long placeId, Date fromDate, Date toDate) throws DateException;

	Map<String, Double> attendanceByTime(Long placeId, Date fromDate, Date toDate) throws DateException;
}

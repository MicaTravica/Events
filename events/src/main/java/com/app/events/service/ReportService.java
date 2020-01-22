package com.app.events.service;

import java.util.Date;
import java.util.HashMap;

public interface ReportService {

	HashMap<String, Double> profitByEvent(Long placeId);

	HashMap<String, Double> attendanceByEvent(Long placeId);

	HashMap<String, Double> profitByTime(Long placeId, Date fromDate, Date toDate);

	HashMap<String, Double> attendanceByTime(Long placeId, Date fromDate, Date toDate);
}

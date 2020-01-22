package com.app.events.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.events.service.ReportService;

@RestController
@RequestMapping("api")
public class ReportController extends BaseController {

	@Autowired
	private ReportService reportService;

	@GetMapping(value = "/report/profit/event", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String, Double>> getProfitEvent(
			@RequestParam(value = "id", required = true) Long placeId) {
		return new ResponseEntity<>(reportService.profitByEvent(placeId), HttpStatus.OK);
	}

	@GetMapping(value = "/report/profit/time", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String, Double>> getProfitTime(
			@RequestParam(value = "id", required = true) Long placeId,
			@RequestParam(value = "fromDate", required = true) Date fromDate,
			@RequestParam(value = "toDate", required = true) Date toDate) throws Exception {
		return new ResponseEntity<>(reportService.profitByTime(placeId, fromDate, toDate), HttpStatus.OK);
	}

	@GetMapping(value = "/report/attendance/event", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String, Double>> getAttendanceEvent(
			@RequestParam(value = "id", required = true) Long placeId) {
		return new ResponseEntity<>(reportService.attendanceByEvent(placeId), HttpStatus.OK);
	}

	@GetMapping(value = "/report/attendance/time", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Map<String, Double>> getAttendanceTime(
			@RequestParam(value = "id", required = true) Long placeId,
			@RequestParam(value = "fromDate", required = true) Date fromDate,
			@RequestParam(value = "toDate", required = true) Date toDate) {
		return new ResponseEntity<>(reportService.attendanceByTime(placeId, fromDate, toDate), HttpStatus.OK);
	}
}

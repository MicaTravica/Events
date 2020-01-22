package com.app.events.serviceimpl;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.app.events.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	// izvestaj o zaradi, naziv dogadjaja i zarada za taj dogadjaj
	@Override
	public HashMap<String, Double> profitByEvent(Long placeId) {
		// TODO Auto-generated method stub
		return null;
	}

	// o posecenosti, naziv dogadjaja, i broj prodatih karata
	@Override
	public HashMap<String, Double> attendanceByEvent(Long placeId) {
		// TODO Auto-generated method stub
		return null;
	}

	// o zaradi za odredjeni vremenski period, godina ili mesec ili  mozda i dan i zarada za taj mesec, godinu, dan  
	@Override
	public HashMap<String, Double> profitByTime(Long placeId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	// o posecenosti za vremenski period i brojd poredatih karata
	@Override
	public HashMap<String, Double> attendanceByTime(Long placeId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

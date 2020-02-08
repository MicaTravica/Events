package com.app.events.serviceimpl.report;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.EventConstants;
import com.app.events.serviceimpl.ReportServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class ReportservicaImplIntegrationTest {


	@Autowired
	private ReportServiceImpl reportService;
	
	
	@Test
	public void profitByEvent_Test_Fail() {
		 Map<String, Double> found = reportService.profitByEvent(EventConstants.INVALID_EVENT_ID);
		 assertTrue(found.isEmpty());
	}
	
	@Test
	public void profitByEvent_Test_Success() {
		 Map<String, Double> found = reportService.profitByEvent(EventConstants.PERSISTED_EVENT_ID);
		 assertFalse(found.isEmpty());
	}


	@Test
	public void attendanceByEvent_Test_Fail() {
		 Map<String, Double> found = reportService.attendanceByEvent(EventConstants.INVALID_EVENT_ID);
		 assertTrue(found.isEmpty());
	}
	
	@Test
	public void attendanceByEvent_Test_Success() {
		 Map<String, Double> found = reportService.attendanceByEvent(EventConstants.PERSISTED_EVENT_ID);
		 assertFalse(found.isEmpty());
	}
	
}

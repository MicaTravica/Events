package com.app.events.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.events.model.Media;
import com.mysql.cj.util.TestUtils;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class MediaServiceControllerTest {
	
	private static final String URL_PREFIX = "/api/media";
	
	private MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	@Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testGetOneMedia() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.get(URL_PREFIX + "/1")
                .accept(MediaType.APPLICATION_JSON_VALUE))
	        	.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.path").value("slicica"))
	        	.andReturn();
	        
    }
    
//    @Test
//    public void testGetStudentsPage() throws Exception {
//    	mockMvc.perform(get(URL_PREFIX + "?page=1&size=" + PAGE_SIZE))
//    		.andExpect(status().isOk())
//    		.andExpect(content().contentType(contentType))
//    		.andExpect(jsonPath("$", hasSize(PAGE_SIZE)))
//    		.andExpect(jsonPath("$.[*].id").value(hasItem(StudentConstants.DB_ID.intValue())))
//            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DB_FIRST_NAME)))
//            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DB_LAST_NAME)))
//            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DB_CARD_NUMBER)));	
//    }
//    
//    @Test
//    public void testGetStudent() throws Exception {
//    	mockMvc.perform(get(URL_PREFIX + "/" + StudentConstants.DB_ID))
//    	.andExpect(status().isOk())
//    	.andExpect(content().contentType(contentType))
//    	.andExpect(jsonPath("$.id").value(StudentConstants.DB_ID.intValue()))
//        .andExpect(jsonPath("$.firstName").value(DB_FIRST_NAME))
//        .andExpect(jsonPath("$.lastName").value(DB_LAST_NAME))
//        .andExpect(jsonPath("$.cardNumber").value(DB_CARD_NUMBER));
//    }
//    
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void testSaveStudent() throws Exception {
//    	Student student = new Student();
//		student.setFirstName(NEW_FIRST_NAME);
//		student.setLastName(NEW_LAST_NAME);
//		student.setCardNumber(NEW_CARD_NUMBER);
//    	
//    	String json = TestUtil.json(student);
//        this.mockMvc.perform(post(URL_PREFIX)
//                .contentType(contentType)
//                .content(json))
//                .andExpect(status().isCreated());
//    }
//    
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void testUpdateStudent() throws Exception {
//    	Student student = new Student();
//    	student.setId(StudentConstants.DB_ID);
//		student.setFirstName(NEW_FIRST_NAME);
//		student.setLastName(NEW_LAST_NAME);
//		student.setCardNumber(NEW_CARD_NUMBER);
//    	
//    	String json = TestUtil.json(student);
//        this.mockMvc.perform(put(URL_PREFIX)
//                .contentType(contentType)
//                .content(json))
//                .andExpect(status().isOk());
//    }
//    
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void testDeleteStudent() throws Exception { 	
//        this.mockMvc.perform(delete(URL_PREFIX + "/" + StudentConstants.DB_ID))
//                .andExpect(status().isOk());
//    }
//    
//    @Test
//    public void testGetStudentByCard() throws Exception {
//    	mockMvc.perform(get(URL_PREFIX + "/findCard?cardNumber=" + DB_CARD_NUMBER))
//    	.andExpect(status().isOk())
//    	.andExpect(content().contentType(contentType))
//    	.andExpect(jsonPath("$.id").value(StudentConstants.DB_ID.intValue()))
//        .andExpect(jsonPath("$.firstName").value(DB_FIRST_NAME))
//        .andExpect(jsonPath("$.lastName").value(DB_LAST_NAME))
//        .andExpect(jsonPath("$.cardNumber").value(DB_CARD_NUMBER));
//    }
//    
//    @Test
//    public void testGetStudentByLastName() throws Exception {
//    	mockMvc.perform(get(URL_PREFIX + "/findLastName?lastName=" + DB_LAST_NAME))
//    	.andExpect(status().isOk())
//    	.andExpect(content().contentType(contentType))
//    	.andExpect(jsonPath("$", hasSize(DB_COUNT_WITH_LAST_NAME)));
//    }
//    
//    @Test
//    public void testGetStudentCourses() throws Exception {
//    	mockMvc.perform(get(URL_PREFIX + "/" + 
//    			StudentConstants.DB_ID_REFERENCED + "/courses"))
//    		.andExpect(status().isOk())
//    		.andExpect(content().contentType(contentType))
//    		.andExpect(jsonPath("$", hasSize(DB_COUNT_STUDENT_COURSES)))
//    		.andExpect(jsonPath("$.[*].id").value(
//    				hasItem(EnrollmentConstants.DB_ID.intValue())))
//    		.andExpect(jsonPath("$.[*].startDate").value(
//    				hasItem(EnrollmentConstants.DB_START_DATE.getTime())))
//    		.andExpect(jsonPath("$.[*].endDate").value(
//    				hasItem(EnrollmentConstants.DB_END_DATE.getTime())))
//    		.andExpect(jsonPath("$.[*].course.id").value(
//    				hasItem(EnrollmentConstants.DB_COURSE_ID.intValue())));
//    }
//    
//    @Test 
//    public void testGetStudentExams() throws Exception {
//    	mockMvc.perform(get(URL_PREFIX + "/" + 
//    			StudentConstants.DB_ID_REFERENCED + "/exams"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(contentType))
//		.andExpect(jsonPath("$", hasSize(DB_COUNT_STUDENT_EXAMS)))
//		.andExpect(jsonPath("$.[*].id").value(
//    			hasItem(ExamConstants.DB_ID.intValue())))
//    	.andExpect(jsonPath("$.[*].date").value(
//    			hasItem(ExamConstants.DB_DATE.getTime())))
//    	.andExpect(jsonPath("$.[*].examPoints").value(
//    			hasItem(ExamConstants.DB_EXAM_POINTS)))
//    	.andExpect(jsonPath("$.[*].labPoints").value(
//    			hasItem(ExamConstants.DB_LAB_POINTS)))
//    	.andExpect(jsonPath("$.[*].course.id").value(
//    			hasItem(ExamConstants.DB_COURSE_ID.intValue())))
//    	.andExpect(jsonPath("$.[*].examPeriod.id").value(
//    			hasItem(ExamConstants.DB_EXAM_PERIOD_ID.intValue())));
//    }
}

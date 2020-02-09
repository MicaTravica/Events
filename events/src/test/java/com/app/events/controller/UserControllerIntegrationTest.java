package com.app.events.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


import com.app.events.constants.UserConstants;
import com.app.events.dto.LoginDTO;
import com.app.events.dto.PasswordChangeDTO;
import com.app.events.dto.UserDTO;
import com.app.events.model.User;
import com.app.events.repository.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class UserControllerIntegrationTest {
	  public static URI uri;
	    public static HttpHeaders headers;

	    @Autowired
	    private TestRestTemplate restTemplate;

	    private String authTokenAdmin = "";

	    @Autowired
	    private UserRepository userRepository;

	    @Before
	    public void login() throws Exception{
	        LoginDTO loginDto = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
	        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", loginDto, String.class);
	        authTokenAdmin = response.getBody();
	    }
	    
	    @Test()
	    public void foundUser_Test_Success() throws Exception{
	        
	        URI uri = new URI("/api/user/" + UserConstants.DB_ADMIN_ID);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	        HttpEntity<String> req = new HttpEntity<>(headers);

	        ResponseEntity<UserDTO> res = restTemplate.exchange(uri, HttpMethod.GET, req, UserDTO.class);

	        assertNotNull(res.getBody());
	        assertEquals(UserConstants.DB_ADMIN_ID, res.getBody().getId());
	        assertEquals(HttpStatus.OK, res.getStatusCode());
	    }
	    
	    
	    @Test()
	    public void foundUser_Test_Fail() throws Exception{
	        
	        URI uri = new URI("/api/user/" + UserConstants.INVALID_ID);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	        HttpEntity<String> req = new HttpEntity<>(headers);

	        ResponseEntity<Object> res = restTemplate.exchange(uri, HttpMethod.GET, req, Object.class);

	        assertNotNull(res.getBody());
	        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	    }

	
	    
	    @Test()
	    public void getRegularUser_Test_Success() throws Exception{
	        
	        URI uri = new URI("/api/regularusers");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	    	HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);


	        ResponseEntity<UserDTO[]> res =    restTemplate.exchange(uri, HttpMethod.GET, httpEntity, UserDTO[].class);

	        assertNotNull(res.getBody());
	        assertEquals(HttpStatus.OK, res.getStatusCode());
	    }

	    @Test()
	    public void getUsers_Test_Success() throws Exception{
	        
	        URI uri = new URI("/api/users");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	    	HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);


	        ResponseEntity<UserDTO[]> res =    restTemplate.exchange(uri, HttpMethod.GET, httpEntity, UserDTO[].class);

	        assertNotNull(res.getBody());
	        assertEquals(HttpStatus.OK, res.getStatusCode());
	    }
	    
	    @Test
	    public void login_Test_Success() throws Exception
	    {

	        LoginDTO content = new LoginDTO(UserConstants.DB_ADMIN_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
	        

	        URI uri = new URI("/api/login");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	        HttpEntity<LoginDTO> req = new HttpEntity<>(content, headers);

	        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.POST, req, String.class);

	        List<User> afterInsert = userRepository.findAll();
	        User addedUser = afterInsert.get(afterInsert.size()-1);

	        assertNotNull(res.getBody());
	        assertEquals(HttpStatus.OK, res.getStatusCode());
	        userRepository.delete(addedUser);
	    }
	    
	    @Test
	    public void login_Test_Fail() throws Exception
	    {

	        LoginDTO content = new LoginDTO(UserConstants.NEW_USERNAME, UserConstants.DB_ADMIN_PASSWORD);
	        

	        URI uri = new URI("/api/login");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	        HttpEntity<LoginDTO> req = new HttpEntity<>(content, headers);

	        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.POST, req, String.class);
	        assertNotNull(res.getBody());
	        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());

	    }

	    @Test
	    public void changePassword_Test_Success() throws Exception
	    {
	    	PasswordChangeDTO cp = new PasswordChangeDTO(UserConstants.DB_USER_PASSWORD, UserConstants.DB_USER_PASSWORD);	        

	        URI uri = new URI("/api/user/password");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	        HttpEntity<PasswordChangeDTO> req = new HttpEntity<>(cp, headers);

	        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.PUT, req, String.class);

			String message = (String) res.getBody();

	        assertNotNull(res.getBody());
	        assertEquals(HttpStatus.OK, res.getStatusCode());
	    }
	    
	    @Test
	    public void changePassword_Test_Fail() throws Exception
	    {
	    	PasswordChangeDTO cp = new PasswordChangeDTO(UserConstants.NEW_PASSWORD, UserConstants.DB_USER_PASSWORD);	        

	        URI uri = new URI("/api/user/password");
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + this.authTokenAdmin);
	        HttpEntity<PasswordChangeDTO> req = new HttpEntity<>(cp, headers);

	        ResponseEntity<String> res = restTemplate.exchange(uri, HttpMethod.PUT, req, String.class);

	        assertNotNull(res.getBody());
	        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());

	    }
	    
	    
	  
	    
	    
	  
}

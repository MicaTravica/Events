package com.app.events.serviceimpl.userDetails;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.UserConstants;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.serviceimpl.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class UserDetailServiceImplIntegrationTest {
	
	 @Autowired
	 private UserDetailsServiceImpl userServiceImpl;

	 
	 @Test
	 public void loadUserByUsername_Test_Success() {
		 UserDetails user = userServiceImpl.loadUserByUsername(UserConstants.DB_ADMIN_USERNAME);
		 
		 assertNotNull(user);
		 assertEquals(UserConstants.DB_ADMIN_USERNAME, user.getUsername());
	 }
	 
	 @Test(expected = UsernameNotFoundException.class)
	 public void loadUserByUsername_Test_Fail() throws ResourceNotFoundException {
		 userServiceImpl.loadUserByUsername(UserConstants.NEW_USERNAME);
		 
	 }
}

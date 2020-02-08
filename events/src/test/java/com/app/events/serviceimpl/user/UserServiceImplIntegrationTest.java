package com.app.events.serviceimpl.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.UserConstants;
import com.app.events.dto.PasswordChangeDTO;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.UserNotFoundByUsernameException;
import com.app.events.model.User;
import com.app.events.serviceimpl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
public class UserServiceImplIntegrationTest {
	 @Autowired
	 private UserServiceImpl userServiceImpl;

	 
	 @Test
	 public void findAll() {
		 Collection<User> users = userServiceImpl.findAll();
		 
		 assertFalse(users.isEmpty());
		 assertSame(2, users.size());
	 }
	 
	 @Test
	 public void findAllRegular() {
		 Collection<User> users = userServiceImpl.findAllRegular();
		 
		 assertFalse(users.isEmpty());
		 assertSame(1, users.size());
	 }
	 
	 @Test
	 public void findOne() throws ResourceNotFoundException {
		 User user = userServiceImpl.findOne(UserConstants.DB_ADMIN_ID);
		 
		 assertNotNull(user);
		 assertEquals(UserConstants.DB_ADMIN_USERNAME, user.getUsername());
	 }
	 
	 @Test(expected = ResourceNotFoundException.class)
	 public void findOne_Test_Fail() throws ResourceNotFoundException {
		 userServiceImpl.findOne(UserConstants.INVALID_ID);
		 
	 }

	 @Test
	 public void findOneByUsername() throws ResourceNotFoundException, UserNotFoundByUsernameException {
		 User user = userServiceImpl.findOneByUsername(UserConstants.DB_ADMIN_USERNAME);
		 
		 assertNotNull(user);
		 assertEquals(UserConstants.DB_ADMIN_USERNAME, user.getUsername());
	 }
	 
	 @Test(expected = UserNotFoundByUsernameException.class)
	 public void findOneByUsername_Test_Fail() throws ResourceNotFoundException, UserNotFoundByUsernameException {
		 userServiceImpl.findOneByUsername(UserConstants.INVALED_USERNAME);
		 
	 }
	 
	 @Test(expected = ResourceExistsException.class)
	 public void registration_Test_Fail() throws Exception {
		 
		 User user = new User(
				 null,
				 "Milica",
				 "Vojnovic",
				 "064",
				 UserConstants.DB_ADMIN_EMAIL,
				 true,
				 UserConstants.NEW_USERNAME,
				 UserConstants.NEW_PASSWORD,
				 null,
				 null
				 );
		 
		 userServiceImpl.registration(user);
	 }
	 
	 
	 @Test(expected = ResourceExistsException.class)
	 public void registration_Test_Fail2() throws Exception {
		 
		 User user = new User(
				 null,
				 "Milica",
				 "Vojnovic",
				 "064",
				 UserConstants.NEW_EMAIL,
				 true,
				 UserConstants.DB_ADMIN_USERNAME,
				 UserConstants.NEW_PASSWORD,
				 null,
				 null
				 );
		 
		 userServiceImpl.registration(user);
	 }
	 
	 @Test(expected = ResourceExistsException.class)
	 public void update_Test_Fail() throws Exception {
		 
		 User user = userServiceImpl.findOne(UserConstants.DB_ADMIN_ID);
		 user.setUsername(UserConstants.DB_USER_USERNAME);
		 userServiceImpl.update(user);
	 }
	 
	 @Test
	 public void update_Test_Success() throws Exception {
		 
		 User user = userServiceImpl.findOne(UserConstants.DB_ADMIN_ID);
		 user.setUsername(UserConstants.INVALED_USERNAME);
		 User updateUser = userServiceImpl.update(user);
		 
		 assertEquals(UserConstants.INVALED_USERNAME, updateUser.getUsername());
		 
		 updateUser.setUsername(UserConstants.DB_ADMIN_USERNAME);
		 userServiceImpl.update(updateUser);
	 }
	 
	 @Test(expected = UserNotFoundByUsernameException.class)
	 public void changePassword_Test_Fail() throws Exception {
		 
		PasswordChangeDTO pc = new PasswordChangeDTO(UserConstants.DB_ADMIN_PASSWORD, UserConstants.NEW_PASSWORD);
				
		userServiceImpl.changeUserPassword(pc, UserConstants.NEW_USERNAME);

	 }
	 
	 @Test
	 public void changePassword_Test_Success() throws Exception {
		 
		PasswordChangeDTO pc = new PasswordChangeDTO(UserConstants.DB_ADMIN_PASSWORD,UserConstants.DB_ADMIN_PASSWORD);
				
		userServiceImpl.changeUserPassword(pc, UserConstants.DB_ADMIN_USERNAME);

	 }





}

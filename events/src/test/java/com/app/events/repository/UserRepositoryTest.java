package com.app.events.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.events.constants.UserConstants;
import com.app.events.model.User;
import com.app.events.model.UserRole;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findByUsername_Test_Success() {
		
		Optional<User> results = userRepository.findByUsername(UserConstants.DB_ADMIN_USERNAME);
		assertTrue(results.isPresent());
		User user = results.get();
		assertEquals(UserConstants.DB_ADMIN_USERNAME, user.getUsername());
	}
	
	@Test
	public void findByUsername_Test_Fail() {
		
		Optional<User> results = userRepository.findByUsername(UserConstants.INVALED_USERNAME);
		assertFalse(results.isPresent());
	
	}
	
	@Test
	public void findByEmail_Test_Success() {
		
		Optional<User> results = userRepository.findByEmail(UserConstants.DB_ADMIN_EMAIL);
		assertTrue(results.isPresent());
		User user = results.get();
		assertEquals(UserConstants.DB_ADMIN_USERNAME, user.getUsername());
		assertEquals(UserConstants.DB_ADMIN_EMAIL, user.getEmail());
	}
	
	@Test
	public void findByEmail_Test_Fail() {
		String email = "mica97";
		
		Optional<User> results = userRepository.findByEmail(email);
		assertFalse(results.isPresent());
	}
	
	@Test
	public void findAllByUserRole_Test_Success_Admin() {
		
		Collection<User> users = userRepository.findAllByUserRole(UserRole.ROLE_ADMIN);
		assertFalse(users.isEmpty());
	}
	
	@Test
	public void findAllByUserRole_Test_Success_Regular() {
		
		Collection<User> users = userRepository.findAllByUserRole(UserRole.ROLE_REGULAR);
		assertFalse(users.isEmpty());	
	}
	
	@Test
	public void findAllByUserRole_Test_Fail() {
		UserRole role = null;
		
		Collection<User> users = userRepository.findAllByUserRole(role);
		assertTrue(users.isEmpty());	
	}
	
}

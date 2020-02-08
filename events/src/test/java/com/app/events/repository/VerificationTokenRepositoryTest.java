package com.app.events.repository;

import static org.junit.Assert.assertFalse;

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
import com.app.events.model.VerificationToken;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestPropertySource("classpath:application-test.properties")
public class VerificationTokenRepositoryTest {
	
	@Autowired
	private VerificationTokenRepository vtRepository;
	
	@Test
	public void findByUserId_Test_Fail() {

		Optional<VerificationToken> results = vtRepository.findById(UserConstants.INVALID_ID);
		assertFalse(results.isPresent());
	}
	
}

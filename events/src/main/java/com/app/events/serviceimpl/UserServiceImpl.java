package com.app.events.serviceimpl;

import java.util.Calendar;
import java.util.Collection;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.events.dto.PasswordChangeDTO;
import com.app.events.exception.PasswordShortException;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.UserNotFoundByUsernameException;
import com.app.events.exception.WrongPasswordException;
import com.app.events.model.User;
import com.app.events.model.UserRole;
import com.app.events.model.VerificationToken;
import com.app.events.repository.UserRepository;
import com.app.events.service.MailService;
import com.app.events.service.UserService;
import com.app.events.service.VerificationTokenService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private VerificationTokenService verificationTokenService;
	
	@Autowired
	private MailService mailService;
	
	@Override
	public User registration(User user) throws MessagingException, PasswordShortException, ResourceExistsException {
		if(userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new ResourceExistsException("Username");
		} else if(userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new ResourceExistsException("Email");
		} else if(user.getPassword().length() < 8) {
			throw new PasswordShortException();
		}
		user.registration();
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user = userRepository.save(user);
		String token = UUID.randomUUID().toString();
		verificationTokenService.create(user, token);
		mailService.newUser(user.getEmail(), token);
		return user;
	}

	@Override
	public Collection<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Collection<User> findAllRegular() {
		return userRepository.findAllByUserRole(UserRole.REGULAR);
	}

	@Override
	public User findOne(Long id) throws ResourceNotFoundException  {
		return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User"));
	}

	@Override
	public User update(User user) throws ResourceNotFoundException, ResourceExistsException {
		User userToUpdate = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User"));
		if(!user.getUsername().equals(userToUpdate.getUsername()) && userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new ResourceExistsException("Username");
		}
		userToUpdate.update(user);
		return userRepository.save(userToUpdate);
	}

	@Override
	public void changeUserPassword(PasswordChangeDTO pcDto, String username) throws WrongPasswordException, UserNotFoundByUsernameException, PasswordShortException {
		User user = userRepository.findByUsername(username).orElseThrow(() ->  new UserNotFoundByUsernameException(username));
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		if(bcpe.matches(pcDto.getOldPassword(), user.getPassword())) {
			if(pcDto.getNewPassword().length() < 8) {
				throw new PasswordShortException();
			}
			user.setPassword(bcpe.encode(pcDto.getNewPassword()));
			userRepository.save(user);
		}else {
			throw new WrongPasswordException();
		}
	}
	
	@Override
	public void verifiedUserEmail(String token) throws ResourceNotFoundException {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
	    User user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	throw new ResourceNotFoundException("User");
	    }
	    user.setVerified(true);
		userRepository.save(user);
	}
	
	@Override
	public User findOneByUsername(String name) throws UserNotFoundByUsernameException {
		return userRepository.findByUsername(name).orElseThrow(() ->  new UserNotFoundByUsernameException(name));
	}

}

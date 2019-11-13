package com.app.events.service;

import java.util.Collection;

import javax.mail.MessagingException;

import com.app.events.dto.PasswordChangeDTO;
import com.app.events.exception.PasswordShortException;
import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.UserNotFoundByUsernameException;
import com.app.events.exception.WrongPasswordException;
import com.app.events.model.User;

public interface UserService {

	User registration(User user) throws MessagingException, PasswordShortException, ResourceExistsException;

	Collection<User> findAll();

	Collection<User> findAllRegular();

	User findOne(Long id) throws ResourceNotFoundException;

	User update(User user) throws ResourceNotFoundException, ResourceExistsException;

	void changeUserPassword(PasswordChangeDTO pcDto, String username) throws WrongPasswordException, UserNotFoundByUsernameException, PasswordShortException;

	User findOneByUsername(String name) throws UserNotFoundByUsernameException;

	void verifiedUserEmail(String token) throws ResourceNotFoundException;

}

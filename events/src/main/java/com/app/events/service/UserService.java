package com.app.events.service;

import java.util.Collection;

import javax.mail.MessagingException;

import com.app.events.dto.PasswordChangeDTO;
import com.app.events.exception.EmailExistsException;
import com.app.events.exception.PasswordShortException;
import com.app.events.exception.TokenNotFoundException;
import com.app.events.exception.UserNotFoundByUsernameException;
import com.app.events.exception.UserNotFoundException;
import com.app.events.exception.UsernameExistsException;
import com.app.events.exception.WrongPasswordException;
import com.app.events.model.User;

public interface UserService {

	User registration(User user) throws UsernameExistsException, EmailExistsException, MessagingException, PasswordShortException;

	Collection<User> findAll();

	Collection<User> findAllRegular();

	User findOne(Long id) throws UserNotFoundException;

	User update(User user) throws UserNotFoundException, UsernameExistsException;

	void changeUserPassword(PasswordChangeDTO pcDto, String username) throws WrongPasswordException, UserNotFoundByUsernameException;

	User findOneByUsername(String name) throws UserNotFoundByUsernameException;

	void verifiedUserEmail(String token) throws TokenNotFoundException;

}

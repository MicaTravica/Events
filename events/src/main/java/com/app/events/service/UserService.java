package com.app.events.service;

import java.util.Collection;

import javax.mail.MessagingException;

import com.app.events.dto.PasswordChangeDTO;
import com.app.events.exception.ExistsException;
import com.app.events.exception.NotFoundException;
import com.app.events.exception.PasswordShortException;
import com.app.events.exception.UserNotFoundByUsernameException;
import com.app.events.exception.WrongPasswordException;
import com.app.events.model.User;

public interface UserService {

	User registration(User user) throws MessagingException, PasswordShortException, ExistsException;

	Collection<User> findAll();

	Collection<User> findAllRegular();

	User findOne(Long id) throws NotFoundException;

	User update(User user) throws NotFoundException, ExistsException;

	void changeUserPassword(PasswordChangeDTO pcDto, String username) throws WrongPasswordException, UserNotFoundByUsernameException;

	User findOneByUsername(String name) throws UserNotFoundByUsernameException;

	void verifiedUserEmail(String token) throws NotFoundException;

}

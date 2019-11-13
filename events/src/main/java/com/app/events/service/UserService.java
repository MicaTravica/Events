package com.app.events.service;

import java.util.Collection;

import com.app.events.dto.PasswordChangeDTO;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.exception.UserNotFoundByUsernameException;
import com.app.events.model.User;

public interface UserService {

	User registration(User user) throws Exception;

	Collection<User> findAll();

	Collection<User> findAllRegular();

	User findOne(Long id) throws ResourceNotFoundException;

	User update(User user) throws Exception;

	void changeUserPassword(PasswordChangeDTO pcDto, String username) throws Exception;

	User findOneByUsername(String name) throws UserNotFoundByUsernameException;

	void verifiedUserEmail(String token) throws ResourceNotFoundException;

}

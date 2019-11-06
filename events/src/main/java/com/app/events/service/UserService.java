package com.app.events.service;

import java.util.Collection;

import com.app.events.dto.PasswordChangeDTO;
import com.app.events.model.User;

public interface UserService {

	User registration(User user) throws Exception;

	Collection<User> findAll();

	Collection<User> findAllRegular();

	User findOne(Long id) throws Exception;

	User update(User user) throws Exception;

	void changeUserPassword(PasswordChangeDTO pcDto, String username) throws Exception;

	User findOneByUsername(String name) throws Exception;

}

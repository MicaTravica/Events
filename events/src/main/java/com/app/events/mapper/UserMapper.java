package com.app.events.mapper;

import java.util.HashSet;

import com.app.events.dto.UserDTO;
import com.app.events.model.User;

public class UserMapper {
	
	public static UserDTO toDTO(User user) {
		return new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getSurname(), user.getPhone(), user.getUsername(), null, user.getUserRole());
	}
	
	public static User toUser(UserDTO userDto) {
		return new User(userDto.getId(), userDto.getName(), userDto.getSurname(), userDto.getPhone(), userDto.getEmail(), false, userDto.getUsername(), userDto.getPassword(), userDto.getUserRole(), new HashSet<>());
	}
}

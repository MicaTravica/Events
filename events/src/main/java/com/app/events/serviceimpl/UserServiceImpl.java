package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.events.model.User;
import com.app.events.model.UserRole;
import com.app.events.repository.UserRepository;
import com.app.events.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User registration(User user) throws Exception {
		if(userRepository.findByUsername(user.getUsername()).isPresent() && userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new Exception();
		}
		user.setUserRole(UserRole.REGULAR);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}

}

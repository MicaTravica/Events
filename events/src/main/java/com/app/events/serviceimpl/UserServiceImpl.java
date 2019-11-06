package com.app.events.serviceimpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.events.dto.PasswordChangeDTO;
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

	@Override
	public Collection<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Collection<User> findAllRegular() {
		return userRepository.findAllByUserRole(UserRole.REGULAR);
	}

	@Override
	public User findOne(Long id) throws Exception {
		return userRepository.findById(id).orElseThrow(()->new Exception());
	}

	@Override
	public User update(User user) throws Exception {
		User userToUpdate = userRepository.findById(user.getId()).orElseThrow(() -> new Exception());
		userToUpdate.update(user);
		return userRepository.save(userToUpdate);
	}

	@Override
	public void changeUserPassword(PasswordChangeDTO pcDto, String username) throws Exception {
		User user = userRepository.findByUsername(username).orElseThrow(() ->  new Exception());
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		if(bcpe.matches(pcDto.getOldPassword(), user.getPassword())) {
			user.setPassword(bcpe.encode(pcDto.getNewPassword()));
			userRepository.save(user);
		}else {
			throw new Exception();
		}
	}

	@Override
	public User findOneByUsername(String name) throws Exception {
		return userRepository.findByUsername(name).orElseThrow(() ->  new Exception());
	}

}

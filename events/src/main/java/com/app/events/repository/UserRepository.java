package com.app.events.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.events.model.User;
import com.app.events.model.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);
	
	Optional<User> findByEmail(String email);

	Collection<User> findAllByUserRole(UserRole role);
}

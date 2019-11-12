package com.app.events.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.events.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	Optional<VerificationToken> findByToken(String token);
	 
	Optional<VerificationToken> findByUserId(Long id);
}

package com.app.events.service;

import com.app.events.exception.TokenNotFoundException;
import com.app.events.model.User;
import com.app.events.model.VerificationToken;

public interface VerificationTokenService {

	void create(User user, String token);

	VerificationToken getVerificationToken(String verificationToken) throws TokenNotFoundException;

}

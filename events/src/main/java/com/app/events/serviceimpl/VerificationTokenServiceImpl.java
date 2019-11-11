package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.events.exception.TokenNotFoundException;
import com.app.events.model.User;
import com.app.events.model.VerificationToken;
import com.app.events.repository.VerificationTokenRepository;
import com.app.events.service.VerificationTokenService;

public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Autowired
	private VerificationTokenRepository verificationTokenRepostory;
	
	@Override
    public VerificationToken getVerificationToken(String verificationToken) throws TokenNotFoundException{
        return verificationTokenRepostory.findByToken(verificationToken).orElseThrow(() -> new TokenNotFoundException());
    }
     
    @Override
    public void create(User user, String token) {
        VerificationToken myToken = new VerificationToken(user, token);
        verificationTokenRepostory.save(myToken);
    }
}
